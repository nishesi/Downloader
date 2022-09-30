package ru.itis.Downloader.commands.executableCommands;


import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.DownloadingThread;
import ru.itis.Downloader.Threads.ThreadEntry;
import ru.itis.Downloader.commands.AbstractCommand;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.itis.Downloader.StaticFields.downloadingDir;


public class StartDownloadingCommand extends AbstractCommand {

  private static final Pattern URLPattern = Pattern.compile(
          "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"
  );


  public StartDownloadingCommand() {
    super("start", "downloads file from specified URL");
  }

  @Override
  public String execute(String[] args, List<ThreadEntry> threads) throws ToFinishException, OperationFailedException {
    if (args.length <= 1) {
      throw new OperationFailedException("not enough arguments");
    }
    Matcher m = URLPattern.matcher(args[1]);
    if (!m.matches()) {
      if (args[1].equals("test") && args.length >= 3) {
        startTestThread(threads, args[2]);
        return "test thread started";
      } else {
        throw new OperationFailedException("uncorrected URL");
      }
    }

    URL url = getURL(args[1]);

    File file = getFinalFile(url);
    if (file.exists()) {
      throw new OperationFailedException("file exists");
    }
    startNewThread(url, file, threads);


    return "downloading started";
  }

  public static URL getURL(String str) throws OperationFailedException {

    Matcher m = URLPattern.matcher(str);

    if (m.find()) {
      try {
        return new URL(m.group());
      } catch (MalformedURLException ex) {
        throw new OperationFailedException("url can not be created");
      }
    } else {
      throw new OperationFailedException("url not found");
    }
  }

  public static File getFinalFile(URL url) throws OperationFailedException {
    Pattern fileNamePattern = Pattern.compile("/([-a-zA-Z0-9()@:%_+.~]+)(\\?|$)");
    Matcher m = fileNamePattern.matcher(url.toString());

    if (m.find()) {
      return downloadingDir.resolve(m.group(1)).toFile();
    } else {
      throw new OperationFailedException("file name not found");
    }
  }

  public static void startNewThread(URL url, File file, List<ThreadEntry> threads) throws OperationFailedException {
    try {
      ThreadEntry threadEntry = new ThreadEntry(new DownloadingThread(url, file));
      threadEntry.getThread().start();
      threads.add(threadEntry);

    } catch (IOException ex) {
      throw new OperationFailedException(ex.getMessage());
    }
  }


  public static void startTestThread(List<ThreadEntry> threads, String name) throws OperationFailedException {
    EndlessInputStream inputStream = new EndlessInputStream();
    File file = downloadingDir.resolve(name).toFile();

//    startNewThread(inputStream, file, threads);
  }



  private static class EndlessInputStream extends InputStream {

    @Override
    public int read() {
      return new Random().nextInt();
    }
  }
}
