package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.dto.response.image.ImageResponseDTO;
import com.hunnit_beasts.kelog.service.ImageService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ImageResponseDTO> uploadImage(
            @Parameter(description = "multipart/form-data 형식의 이미지")
            @RequestPart("multipartFile")
            MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageService.uploadFile(file));
    }

    @GetMapping("/{url}")
    public void getImage(@PathVariable(value = "url") String url) {
        throw new UnsupportedOperationException();
    }
}
