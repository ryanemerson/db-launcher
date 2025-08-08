package io.github.ryanemerson.db.launcher;

import java.util.List;

public class MySqlVendor extends DatabaseVendor {

    public MySqlVendor() {
        super("mirror.gcr.io/mysql", 3306, "8.4", "test", "test", "test");
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
        return "mysql";
    }
}
