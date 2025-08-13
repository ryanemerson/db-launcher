package io.github.ryanemerson.db.launcher;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import org.jboss.logging.Logger;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.List;

public class DockerService {

    private static final Logger LOG = Logger.getLogger(DockerService.class);
    private final DockerClient dockerClient;

    public DockerService() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    public void startContainer(DatabaseVendor vendor, String version, DatabaseConfig config) {
        String imageName = vendor.getImageName(version);
        String containerName = vendor.getContainerName();

        if (!imageExists(imageName)) {
            LOG.infof("Image not found locally, pulling from registry: %s", imageName);
            try {
                dockerClient.pullImageCmd(imageName).exec(new PullImageResultCallback()).awaitCompletion();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        List<String> env = vendor.getEnvVariables(config);

        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withEnv(env)
                .withHostConfig(new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(vendor.getPort()), new ExposedPort(vendor.getPort()))))
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
        LOG.infof("Container started: %s", container.getId());
    }

    public void stopContainer(DatabaseVendor vendor) {
        String containerName = vendor.getContainerName();
        Container container = getContainer(containerName);
        if (container != null) {
            dockerClient.stopContainerCmd(container.getId()).exec();
            dockerClient.removeContainerCmd(container.getId()).exec();
            LOG.infof("Container stopped and removed: %s", container.getId());
        } else {
            LOG.errorf("Container not found: %s", containerName);
        }
    }

    public void logsContainer(DatabaseVendor vendor, boolean follow) {
        String containerName = vendor.getContainerName();
        Container container = getContainer(containerName);
        if (container != null) {
            LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(container.getId())
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(follow);
            try {
                logContainerCmd.exec(new LogContainerResultCallback() {
                    @Override
                    public void onNext(Frame item) {
                        LOG.info(item.toString());
                    }
                }).awaitCompletion();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            LOG.errorf("Container not found: %s", containerName);
        }
    }

    public List<Container> listContainers() {
        return dockerClient.listContainersCmd().withShowAll(false).exec();
    }

    private boolean imageExists(String imageName) {
        List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();
        return !images.isEmpty();
    }

    private Container getContainer(String containerName) {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).withNameFilter(List.of(containerName)).exec();
        return containers.isEmpty() ? null : containers.get(0);
    }
}
