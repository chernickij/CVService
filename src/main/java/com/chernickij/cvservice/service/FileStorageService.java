package com.chernickij.cvservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void saveFile(MultipartFile file, String fileName);

    Resource getFile(String fileName);
}
