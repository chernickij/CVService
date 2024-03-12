package com.chernickij.cvservice.service.impl;

import com.chernickij.cvservice.exception.BadRequestException;
import com.chernickij.cvservice.exception.ResourceNotFoundException;
import com.chernickij.cvservice.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${file-storage.directory.root-path}")
    private String rootPath;

    private final Path rootLocation;

    public FileStorageServiceImpl(@Value("${file-storage.directory.root-path}") final String rootPath) {
        this.rootLocation = Paths.get(rootPath);
    }

    @Override
    public void saveFile(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new BadRequestException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(rootPath + fileName)).normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file.", e);
        }
    }

    @Override
    public Resource getFile(String fileName) {
        try {
            Path file = rootLocation.resolve(rootPath + fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File with name {} not found " + fileName);
            }
        } catch (MalformedURLException e){
            throw new ResourceNotFoundException("File with name {} not found " + fileName, e);
        }
    }
}
