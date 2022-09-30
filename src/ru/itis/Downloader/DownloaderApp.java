package ru.itis.Downloader;

import ru.itis.Downloader.Exceptions.OperationFailedException;
import ru.itis.Downloader.Exceptions.ToFinishException;
import ru.itis.Downloader.Threads.ThreadEntry;
import ru.itis.Downloader.Threads.UserCommunicatorThread;
import ru.itis.Downloader.commands.Commands;
import ru.itis.Downloader.commands.Executable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DownloaderApp {
    protected Map<String, ? extends Executable> commandsMap;
    protected UserCommunicatorThread requester;
    protected Thread requesterThread;
    protected LinkedList<ThreadEntry> threads;

    private static void checkFinishedThreads(List<ThreadEntry> threads) {
        int i = 1;
        for (ThreadEntry entry : threads) {
            if (!entry.getThread().isAlive()) {
                System.out.println(i + " " + entry.getName() + " downloading finished");
                threads.remove(entry);
            }
            i++;
        }
    }

    public void init() throws Exception {

        try {
            StaticFields.validateStaticFields();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        commandsMap = Commands.getCommands();
        requester = new UserCommunicatorThread(System.in);
        requesterThread = new Thread(requester);
        threads = new LinkedList<>();

        if (CachingDownloadingThreads.loadCache(threads)) {
            System.out.println("cached loadings reloaded");
        }
    }

    public void start() throws InterruptedException {
        requesterThread.start();

        for (ThreadEntry entry : threads) {
            entry.getThread().start();
            System.out.println(entry.getName() + " resumed");
        }


        while (true) {
            checkFinishedThreads(threads);

            String request = requester.getRequest();

            if (request == null) {
                Thread.sleep(1000);
                continue;
            }

            String[] requestArr = request.split(" ");
            Executable cmd = commandsMap.get(requestArr[0]);
            try {

                if (cmd != null) {
                    String mess = cmd.execute(requestArr, threads);
                    System.out.println(mess);
                } else {
                    System.out.println("unknown command");
                }

            } catch (ToFinishException ex) {

                requesterThread.interrupt();
                System.out.println(ex.getMessage());
                break;

            } catch (OperationFailedException ex) {
                System.out.println(ex.getMessage());

            }
        }
    }
}

