package com.zack.api.components.files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class FileManager {
    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);

    public  void writeStreamFile(String filePath, InputStream fileInput) throws IOException {

        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInput.read(buffer)) != -1) {
            logger.info("file write: " + (float) length / 1024 + " kb");
            outputStream.write(buffer, 0, length);
        }

    }
}
