package ru.itis.Downloader;

public class Main {
    public static void main(String[] args) {
        DownloaderApp app = new DownloaderApp();
        System.out.println("test");

        try {
            app.init();
            app.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

