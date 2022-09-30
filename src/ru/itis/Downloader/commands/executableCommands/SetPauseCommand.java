package ru.itis.Downloader.commands.executableCommands;

import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.ThreadEntry;
import ru.itis.Downloader.commands.AbstractCommand;

import java.util.List;

public class SetPauseCommand extends AbstractCommand {

  public SetPauseCommand() {
    super("pause", "pauses specified downloading");
  }

  @Override
  public String execute(String[] args, List<ThreadEntry> threads) throws ToFinishException, OperationFailedException {
    if (args.length < 3) {
      throw new OperationFailedException("not enough arguments");
    }

    int index = Integer.parseInt(args[1])-1;

    if (validateIndex(index, threads)) {
      ThreadEntry entry = threads.get(index);
      entry.getDownloadingThread().setPause(Boolean.parseBoolean(args[2]));
    } else {
      throw new OperationFailedException("uncorrect number of thread");
    }
    return "thread paused";
  }
}
