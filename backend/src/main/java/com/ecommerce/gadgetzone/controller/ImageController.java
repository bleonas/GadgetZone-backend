package com.ecommerce.gadgetzone.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            String FILE_PATH_ROOT = "src/main/resources/static/";
            image = FileUtils.readFileToByteArray(new File(FILE_PATH_ROOT +filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
