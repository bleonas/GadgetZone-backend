package com.ecommerce.gadgetzone.service.interfaces;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

    String saveImage(File path, MultipartFile profileImage, String imageName) throws IOException;
    
    String deleteImage(String imagePath);
}
