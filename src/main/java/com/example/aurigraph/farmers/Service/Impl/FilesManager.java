package com.example.aurigraph.farmers.Service.Impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesManager {
    public static String saveFile(MultipartFile file,String imageFolder1, String imageFolder2,String entityName,  String doc, String fileType) throws IOException {
        // Replace spaces with underscores in the entityName, imageType, and table
        entityName = entityName.replace(" ", "_");
        fileType = fileType.replace(" ", "_");
        doc = doc.replace(" ", "_");

        // Use the base directory with the correct separator
        String baseDir = System.getProperty("user.dir") + File.separator + "src" + File.separator +
                "main" + File.separator + "resources" + File.separator + "static" +
                File.separator + "images" + File.separator+ imageFolder1+ File.separator + imageFolder2+ File.separator + entityName + File.separator + doc ;

        File dir = new File(baseDir);

        // Construct file name and path in an OS-independent manner
        String [] fileTypeParts = fileType.split("/");
        fileType = fileTypeParts[fileTypeParts.length-1];
        String fileName = doc + "." + fileType;
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

        Path filePath = serverFile.toPath();
        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        } catch (IOException e) {
            System.err.println("File already exists or error creating file: " + e.getMessage());
        }

        // Construct the relative path for saving the file
        String path = "images" + "/" + imageFolder1 + "/" + imageFolder2+ "/"+ entityName + "/" + doc + "/" + fileName;
        String targetFilePath = "images"+ File.separator+ imageFolder1 + File.separator + imageFolder2 + File.separator + entityName + File.separator + doc + File.separator + fileName;

        // Use try-with-resources to ensure streams are closed properly
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(serverFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyFileToTarget(serverFile, targetFilePath);
        return path;
    }

    private static void copyFileToTarget(File file, String path) {
        Path targetPath = Path.of("target" + File.separator + "classes" + File.separator + "static" + File.separator + path);
        try {
            Files.createDirectories(targetPath.getParent()); // Create target directories if they don't exist
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied to target: " + targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
