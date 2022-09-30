package ru.itis.Downloader.Threads;

public class AbstractDownloaderThread {
  protected boolean isFinished;

  public AbstractDownloaderThread() {
    this.isFinished = false;
  }

  public void toFinish() {
    this.isFinished = true;
  }
}
