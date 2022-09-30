package ru.itis.Downloader;

import ru.itis.Downloader.Threads.DownloadingThread;
import ru.itis.Downloader.Threads.ThreadEntry;

import java.io.*;
import java.util.List;

import static ru.itis.Downloader.StaticFields.cache;

public class CachingDownloadingThreads {

    public static boolean loadCache(List<ThreadEntry> list) {
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     new FileInputStream(cache)
                             )
                     )
        ) {
            int size = in.readInt();

            for (int i = 0; i < size; i++) {
                list.add(new ThreadEntry((DownloadingThread) in.readObject()));
            }


            return true;


        } catch (EOFException | FileNotFoundException | ClassNotFoundException ex) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean toCache(List<ThreadEntry> list) {
        checkThreadsFinishing(list);

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new BufferedOutputStream(
                                     new FileOutputStream(cache)
                             )
                     )
        ) {

            out.writeInt(list.size());

            for (ThreadEntry entry : list) {
                out.writeObject(entry.getDownloadingThread());
            }
            out.flush();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void checkThreadsFinishing(List<ThreadEntry> list) {
        for (ThreadEntry entry : list) {
            if (entry.isAlive()) {
                throw new RuntimeException("thread is alive");
            }
        }
    }
}

