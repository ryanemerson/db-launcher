package io.github.ryanemerson.db.launcher;

import java.util.List;

public abstract class DatabaseVendor {

    private final String imageName;
    private final int port;
    private final String defaultVersion;
    private final String defaultDbName;
    private final String defaultUsername;
    private final String defaultPassword;

    protected DatabaseVendor(String imageName, int port, String defaultVersion, String defaultDbName, String defaultUsername, String defaultPassword) {
        this.imageName = imageName;
        this.port = port;
        this.defaultVersion = defaultVersion;
        this.defaultDbName = defaultDbName;
        this.defaultUsername = defaultUsername;
        this.defaultPassword = defaultPassword;
    }

    public String getImageName(String version) {
        return imageName + ":" + (version != null ? version : defaultVersion);
    }

    public int getPort() {
        return port;
    }

    public String getContainerName() {
        return "db-" + this.toString();
    }

    public String getDefaultDbName() {
        return defaultDbName;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public abstract List<String> getEnvVariables(DatabaseConfig config);

    @Override
    public abstract String toString();
}