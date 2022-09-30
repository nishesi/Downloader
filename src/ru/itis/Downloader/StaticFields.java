package ru.itis.Downloader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticFields {
  public static final Path downloadingDir = Paths.get("D:/JavaDownloads");

  public static final File cache = Paths
          .get("src/ru/kfu/itis/zaripov/studying/homework/Downloader/cache")
          .toAbsolutePath()
          .normalize()
          .toFile();

  public static void validateStaticFields() throws Exception {
    File dir = downloadingDir.toFile();

    if (!dir.exists()) {
      dir.mkdirs();
    } else {
      if (dir.isFile()) {
        throw new Exception("testdownloading directory is set incorrectly");
      }
    }

    if (!cache.exists()) {
      cache.createNewFile();
    } else if (cache.isDirectory()) {
      throw new Exception("testcache file is set incorrectly");
    }
  }
}
