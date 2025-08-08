package io.github.ryanemerson.db.launcher;

import java.util.ArrayList;
import java.util.List;

public class OracleVendor extends DatabaseVendor {

    public OracleVendor() {
        super("mirror.gcr.io/gvenzl/oracle-free", 1521, "23.5-slim-faststart", "freepdb1", "test", "test");
    }

    @Override
    public List<String> getEnvVariables(DatabaseConfig config) {
        List<String> env = new ArrayList<>();
        env.add("ORACLE_PASSWORD=" + config.password());
        if (!config.dbName().equals(getDefaultDbName())) {
            env.add("ORACLE_DATABASE=" + config.dbName());
        }
        env.add("APP_USER=" + config.username());
        env.add("APP_USER_PASSWORD=" + config.password());
        return env;
    }

    @Override
    public String toString() {
        return "oracle";
    }
}
