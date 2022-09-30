package ru.itis.Downloader.commands.executableCommands;

import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.ThreadEntry;
import ru.itis.Downloader.commands.AbstractCommand;

import java.util.List;

public class ThreadFinishCommand extends AbstractCommand {

  public ThreadFinishCommand() {
    super("finish", "finishes specified thread");
  }

  @Override
  public String execute(String[] args, List<ThreadEntry> threads) throws ToFinishException, OperationFailedException {

    int index = Integer.parseInt(args[1])-1;

    if (validateIndex(index, threads)) {
      ThreadEntry entry = threads.get(index);
      entry.getDownloadingThread().setFinished(true);
    } else {
      throw new OperationFailedException("ucnorrect number of thread");
    }

    return "thread finished";
  }
}
