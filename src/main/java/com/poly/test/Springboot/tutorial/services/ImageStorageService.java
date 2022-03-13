package com.poly.test.Springboot.tutorial.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
// Class ImageStorageService Để thực thi interface IStorageService
public class ImageStorageService implements IStorageService{
    private final Path storageFolder = Paths.get("Uploads");
    //Constructor
    public ImageStorageService(){
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception){
            throw new RuntimeException("Cannot intialize storage", exception);
        }
    }

    private boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storFile(MultipartFile file) {
        try {
            System.out.println("$$$");
            if (file.isEmpty()){
                throw new RuntimeException("Failed to store emty file.");
            }
            //Check file is image
            if (!isImageFile(file)){
                throw new RuntimeException("You can only upload image file. ");
            }
            // File must be <= 5Mb
            float fileSizeInMegabytes = file.getSize() / 1_000_000;
            if (fileSizeInMegabytes > 5.0f){
                throw new RuntimeException("File must be < 5Mb. ");
            }
            //File must be rename --> Do not delete old files with the same name
            // Generated file name
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationaFilePath = this.storageFolder.resolve(
                    Paths.get(generatedFileName))
                            .normalize().toAbsolutePath();
            if (!destinationaFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException(
                        "Cannot store file outside current directory. "
                );
            }
            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationaFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        }catch (Exception exception){
            throw new RuntimeException("Failed to store emty file.", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {

    }
}
