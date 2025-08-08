package io.github.ryanemerson.db.launcher;

import java.util.List;

public class MssqlVendor extends DatabaseVendor {

    public MssqlVendor() {
        super("mcr.microsoft.com/mssql/server", 1433, "2022-latest", null, null, "test");
    }

    @Override
    public List<String> getEnvVariables(DatabaseConfig config) {
        return List.of(
                "ACCEPT_EULA=Y",
                "SA_PASSWORD=" + config.password());
    }

    @Override
    public String toString() {
        return "mssql";
    }
}
