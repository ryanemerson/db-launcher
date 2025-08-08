package io.github.ryanemerson.db.launcher;

import java.util.List;

public class MariaDbVendor extends DatabaseVendor {

    public MariaDbVendor() {
        super("mirror.gcr.io/mariadb", 3306, "11.4", "test", "test", "test");
    }

    @Override
    public List<String> getEnvVariables(DatabaseConfig config) {
        return List.of(
                "MYSQL_DATABASE=" + config.dbName(),
                "MYSQL_USER=" + config.username(),
                "MYSQL_PASSWORD=" + config.password(),
                "MYSQL_ROOT_PASSWORD=" + config.password());
    }

    @Override
    public String toString() {
        return "mariadb";
    }
}
