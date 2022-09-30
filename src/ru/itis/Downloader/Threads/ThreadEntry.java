package ru.itis.Downloader.Threads;

import java.io.Serializable;

public class ThreadEntry extends Thread implements Serializable {
  private DownloadingThread downloadingThread;
  private Thread thread;

  public ThreadEntry(DownloadingThread downloadingThread) {
    this.downloadingThread = downloadingThread;
    this.thread = new Thread(downloadingThread);
    super.setName(downloadingThread.getName());
  }

  public Thread getThread() {
    return thread;
  }

  public DownloadingThread getDownloadingThread() {
    return downloadingThread;
  }
}
