package ru.itis.Downloader.Threads;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class UserCommunicatorThread extends AbstractDownloaderThread implements Runnable{
  private final Scanner sc;
  private final Queue<String> requests;

  public UserCommunicatorThread(InputStream in) {
    this.sc = new Scanner(in);
    this.requests = new LinkedList<>();
  }

  @Override
  public void run() {
    while (!isFinished && sc.hasNextLine()) {
      String request = sc.nextLine();
      requests.add(request);

      if (request.equals("exit")) {
        break;
      }
    }
  }

  public String getRequest() {
    return requests.poll();
  }
}

