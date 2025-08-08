package io.github.ryanemerson.db.launcher;

import picocli.CommandLine;

public class DatabaseVendorConverter implements CommandLine.ITypeConverter<DatabaseVendor> {

    @Override
    public DatabaseVendor convert(String value) {
        return switch (value.toLowerCase()) {
            case "postgres" -> new PostgresVendor();
            case "mysql" -> new MySqlVendor();
            case "mariadb" -> new MariaDbVendor();
            case "oracle" -> new OracleVendor();
            case "mssql" -> new MssqlVendor();
            default -> throw new CommandLine.TypeConversionException("Unsupported database vendor: " + value);
        };
    }
}
