package io.github.ryanemerson.db.launcher;

import com.github.dockerjava.api.model.Container;

import org.jboss.logging.Logger;

import picocli.CommandLine.Command;

import java.util.List;

@Command(name = "list", description = "List running database containers")
public class ListCommand implements Runnable {

    private static final Logger LOG = Logger.getLogger(ListCommand.class);

    @Override
    public void run() {
        DockerService dockerService = new DockerService();
        List<Container> containers = dockerService.listContainers();

        for (Container container : containers) {
            LOG.infof("Name: %s Image: %s Status %s", String.join(", ",
                        container.getNames()),
                  container.getImage(),
                  container.getStatus()
            );
        }
    }
}
