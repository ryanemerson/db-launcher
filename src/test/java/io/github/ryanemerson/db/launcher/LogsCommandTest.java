package io.github.ryanemerson.db.launcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogsCommandTest {

    @Mock
    private DockerService dockerService;

    @Test
    void testRun() {
        LogsCommand logsCommand = new LogsCommand();
        logsCommand.vendor = new PostgresVendor();
        logsCommand.dockerService = dockerService;

        logsCommand.run();

        verify(dockerService).logsContainer(logsCommand.vendor);
    }
}
