package io.github.ryanemerson.db.launcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpCommandTest {

    @Mock
    private DockerService dockerService;

    @Test
    void testRun() {
        UpCommand upCommand = new UpCommand();
        upCommand.vendor = new PostgresVendor();
        upCommand.version = "13";
        upCommand.dbName = "my_db";
        upCommand.username = "my_user";
        upCommand.password = "my_password";
        upCommand.dockerService = dockerService;

        upCommand.run();

        DatabaseConfig config = new DatabaseConfig("my_db", "my_user", "my_password");
        verify(dockerService).startContainer(upCommand.vendor, "13", config);
    }

    @Test
    void testRunWithoutVersion() {
        UpCommand upCommand = new UpCommand();
        upCommand.vendor = new PostgresVendor();
        upCommand.dbName = "my_db";
        upCommand.username = "my_user";
        upCommand.password = "my_password";
        upCommand.dockerService = dockerService;

        upCommand.run();

        DatabaseConfig config = new DatabaseConfig("my_db", "my_user", "my_password");
        verify(dockerService).startContainer(upCommand.vendor, null, config);
    }

    @Test
    void testRunWithoutOptionalArgs() {
        UpCommand upCommand = new UpCommand();
        upCommand.vendor = new PostgresVendor();
        upCommand.dockerService = dockerService;

        upCommand.run();

        DatabaseConfig config = new DatabaseConfig("test", "test", "test");
        verify(dockerService).startContainer(upCommand.vendor, null, config);
    }
}
