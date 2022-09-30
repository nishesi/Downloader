package ru.itis.Downloader.commands.executableCommands;

import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.DownloadingThread;
import ru.itis.Downloader.Threads.ThreadEntry;
import ru.itis.Downloader.commands.AbstractCommand;

import java.util.List;

public class InformationPrintCommand extends AbstractCommand {

  public InformationPrintCommand() {
    super("inf", "prints downloading threads");
  }

  @Override
  public String execute(String[] args, List<ThreadEntry> threads) throws ToFinishException, OperationFailedException {
    int i = 1;
    for (ThreadEntry entry : threads) {
      DownloadingThread t = entry.getDownloadingThread();

      String status = String.valueOf((double)Math.round(t.getDownloaded()*1000) / 10) + "%";

      System.out.println(i + " " + entry.getName()  + "; Pause: " + t.getPause() + "; downloaded: " + status);
      i++;
    }

    if (threads.isEmpty()) {
      System.out.println("nothing downloading");
    }
    return "";
  }
}

