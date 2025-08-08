package io.github.ryanemerson.db.launcher;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "up", description = "Start a database container")
public class UpCommand implements Runnable {

    @Parameters(index = "0", description = "Database vendor. Valid values: postgres, mysql, mariadb, oracle, mssql", converter = DatabaseVendorConverter.class)
    DatabaseVendor vendor;

    @Option(names = {"--version"}, description = "Database version")
    String version;

    @Option(names = {"-n", "--name"}, description = "Database name")
    String dbName;

    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Option(names = {"-p", "--password"}, description = "Password")
    String password;

    DockerService dockerService = new DockerService();

    @Override
    public void run() {
        DatabaseConfig config = new DatabaseConfig(
                dbName != null ? dbName : vendor.getDefaultDbName(),
                username != null ? username : vendor.getDefaultUsername(),
                password != null ? password : vendor.getDefaultPassword()
        );
        dockerService.startContainer(vendor, version, config);
    }
}
