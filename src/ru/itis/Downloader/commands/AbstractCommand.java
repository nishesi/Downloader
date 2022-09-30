package ru.itis.Downloader.commands;


import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.ThreadEntry;

import java.util.List;

public abstract class AbstractCommand implements Executable {
  protected String name;
  protected String message;

  public AbstractCommand(String name, String message) {
    this.name = name;
    this.message = message;;
  }

  public String getName() {
    return name;
  }

  public String message() {
    return message;
  }



  @Override
  public abstract String execute(String[] args, List<ThreadEntry> threads)
          throws ToFinishException, OperationFailedException;


  public static <E> boolean validateIndex(int index, List<E> list) {
    if (index < 0 || index >= list.size()) {
      return false;
    } else {
      return true;
    }
  }
}
