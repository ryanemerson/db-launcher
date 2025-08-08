package io.github.ryanemerson.db.launcher;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "down", description = "Stop and remove a database container")
public class DownCommand implements Runnable {

    @Parameters(index = "0", description = "Database vendor to stop. Valid values: postgres, mysql, mariadb, oracle, mssql", converter = DatabaseVendorConverter.class)
    DatabaseVendor vendor;

    DockerService dockerService = new DockerService();

    @Override
    public void run() {
        dockerService.stopContainer(vendor);
    }
}