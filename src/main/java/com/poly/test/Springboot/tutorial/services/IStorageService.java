package com.poly.test.Springboot.tutorial.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public String storFile(MultipartFile file);
    public Stream<Path> loadAll(); // Load all file inside a folder
    public byte[] readFileContent(String fileName); // chứa danh sách các ảnh (mảng)
    public void deleteAllFiles();
}
