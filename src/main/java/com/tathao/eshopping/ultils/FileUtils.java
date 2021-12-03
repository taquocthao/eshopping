package com.tathao.eshopping.ultils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String uploadFile(String path, String fileName, MultipartFile multipartFile) throws IOException {
        logger.info("Upload file - path: " + path + " - file name: " + fileName);
        Path uploadPath = Paths.get(path);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("FileUtils - method uploadFile success: " + path + fileName);
            return path + fileName;
        } catch (IOException e) {
            logger.error("Could not save file: " + fileName, e);
            return "";
        }
    }
}
