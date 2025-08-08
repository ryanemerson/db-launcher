package io.github.ryanemerson.db.launcher;

import java.util.List;

public class PostgresVendor extends DatabaseVendor {

    public PostgresVendor() {
        super("mirror.gcr.io/postgres", 5432, "17", "test", "test", "test");
    }

    @Override
    public List<String> getEnvVariables(DatabaseConfig config) {
        return List.of(
                "POSTGRES_DB=" + config.dbName(),
                "POSTGRES_USER=" + config.username(),
                "POSTGRES_PASSWORD=" + config.password());
    }

    @Override
    public String toString() {
        return "postgres";
    }
}
