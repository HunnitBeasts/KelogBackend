package com.hunnit_beasts.kelog.controllerv2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/images")
public class ImageController {

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable Long imageId) {
        throw new UnsupportedOperationException();
    }
}
