package io.github.ryanemerson.db.launcher;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "logs", description = "Fetch the logs of a database container")
public class LogsCommand implements Runnable {

    @Parameters(index = "0", description = "Database vendor. Valid values: postgres, mysql, mariadb, oracle, mssql", converter = DatabaseVendorConverter.class)
    DatabaseVendor vendor;

    @Option(names = {"-f", "--follow"}, description = "Follow log output")
    boolean follow;

    DockerService dockerService = new DockerService();

    @Override
    public void run() {
        dockerService.logsContainer(vendor, follow);
    }
}
