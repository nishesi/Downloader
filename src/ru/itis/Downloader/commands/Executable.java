package ru.itis.Downloader.commands;


import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.ThreadEntry;

import java.util.List;

public interface Executable {

  String execute(String[] args, List<ThreadEntry> threads) throws ToFinishException, OperationFailedException;

}
