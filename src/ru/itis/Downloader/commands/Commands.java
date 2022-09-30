package ru.itis.Downloader.commands;

import ru.itis.Downloader.commands.executableCommands.*;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private static final Map<String, AbstractCommand> commandMap = new HashMap<>();

    public static Map<String, AbstractCommand> getCommands() {
        if (commandMap.isEmpty()) {
            AbstractCommand[] arr =
                    {new StartDownloadingCommand(),
                            new InformationPrintCommand(),
                            new SetPauseCommand(),
                            new ThreadFinishCommand(),
                            new ExitCommand()
                    };

            for (AbstractCommand abstractCommand : arr) {
                commandMap.put(abstractCommand.getName(), abstractCommand);
            }
        }
        return commandMap;
    }

}
