package io.github.ryanemerson.db.launcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DownCommandTest {

    @Mock
    private DockerService dockerService;

    @Test
    void testRun() {
        DownCommand downCommand = new DownCommand();
        downCommand.vendor = new PostgresVendor();
        downCommand.dockerService = dockerService;

        downCommand.run();

        verify(dockerService).stopContainer(downCommand.vendor);
    }
}