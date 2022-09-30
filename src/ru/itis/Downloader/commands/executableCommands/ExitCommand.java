package ru.itis.Downloader.commands.executableCommands;

import ru.itis.Downloader.CachingDownloadingThreads;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.ThreadEntry;
import ru.itis.Downloader.commands.AbstractCommand;

import java.util.List;


public class ExitCommand extends AbstractCommand {

  public ExitCommand() {
    super("exit", "finishes file managers work");
  }

  @Override
  public String execute(String[] args, List<ThreadEntry> threads) throws ToFinishException {

    for (ThreadEntry entry : threads) {
      entry.getDownloadingThread().preserve();
    }

    allThreadsFinishingWaiting(threads);

    if (CachingDownloadingThreads.toCache(threads)) {
      System.out.println("downloadings cached");
    }

    throw new ToFinishException("program finished");
  }

  private void allThreadsFinishingWaiting(List<ThreadEntry> threads) {

    boolean flag = true;

    while (flag) {
      flag = false;
      for (ThreadEntry entry : threads) {
        if (entry.getThread().isAlive()) {
          flag = true;
          break;
        }
      }

      if (flag) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

