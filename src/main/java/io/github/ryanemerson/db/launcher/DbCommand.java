package io.github.ryanemerson.db.launcher;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@TopCommand
@Command(name = "db",
        mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "A CLI to manage local database containers",
        subcommands = {
                UpCommand.class,
                DownCommand.class,
                ListCommand.class,
                LogsCommand.class,
                HelpCommand.class
        })
public class DbCommand {
}
