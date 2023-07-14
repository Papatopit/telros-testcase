package com.telros.telrostestcase.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    String uploadPhoto(MultipartFile file);

    Resource loadAsResource(String filename);

    String deleteFile(String filename);
}
