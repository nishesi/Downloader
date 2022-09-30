package ru.itis.Downloader.Threads;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DownloadingThread extends AbstractDownloaderThread implements Runnable, Serializable {
    private final URL url;
    private final File file;
    private final long fileSize;
    private boolean pause = false;
    private long downloaded;

    public DownloadingThread(URL url, File file) throws IOException {
        this.url = url;
        this.file = file;
        this.downloaded = 0;

        URLConnection cn = url.openConnection();
        fileSize = cn.getContentLength();

        try {
            if (!file.createNewFile()) {
                if (file.isDirectory()) {
                    System.out.println("specified file is a directory");
                } else {
                    System.out.println("specified file is exist");
                }
                isFinished = true;
            }
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    @Override
    public void run() {
        isFinished = false;
        int bufferSize = 8192;
        byte[] buffer = new byte[bufferSize];

        try (BufferedInputStream in =
                     new BufferedInputStream(url.openStream(), bufferSize);
             BufferedOutputStream out =
                     new BufferedOutputStream(
                             new FileOutputStream(file, true), bufferSize)
        ) {

            if (downloaded != in.skip(downloaded)) {
                throw new RuntimeException("downloading failed");
            }

            while (!isFinished) {
                if (pause) {
                    Thread.sleep(1000);
                } else {
                    if (!writeBuffer(in, out, buffer)) {
                        isFinished = true;
                    }
                }
            }
            out.flush();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setFinished(boolean finished) {
        this.isFinished = finished;
    }

    private boolean writeBuffer(BufferedInputStream in,
                                BufferedOutputStream out,
                                byte[] buffer
    ) throws IOException {

        int count = in.read(buffer);

        if (count == -1) {
            return false;

        } else {
            downloaded += count;

            if (count == buffer.length) {
                out.write(buffer);
                return true;

            } else {
                out.write(buffer, 0, count);
                return false;
            }
        }
    }

    public String getName() {
        return file.getName();
    }

    public double getDownloaded() {
        if (fileSize > 0) {
            return (double) downloaded / fileSize;
        } else {
            return -1;
        }
    }

    public void preserve() {
        this.isFinished = true;
    }

    public void depreserve() {
        this.isFinished = false;
    }
}

