package com.navec.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageImpl implements Storage {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @Override
    public void putFile(String filename, InputStream stream) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, filename);
        Files.createFile(fileNameAndPath);
        try (OutputStream outputStream = new FileOutputStream(fileNameAndPath.toFile())) {
            outputStream.write(stream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
