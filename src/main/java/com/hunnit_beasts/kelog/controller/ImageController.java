package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.dto.response.image.ImageResponseDTO;
import com.hunnit_beasts.kelog.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth")) // 이거 쓰면 토큰 집어 넣어서 사용할 수 있음
    // https://velog.io/@steady_aa/Spring-Doc%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-swagger-%EC%B6%94%EA%B0%80 확인요망
    public ResponseEntity<ImageResponseDTO> uploadImage(
            @Parameter(description = "multipart/form-data 형식의 이미지")
            @RequestPart("multipartFile")
            MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageService.uploadFile(file));
    }

    @GetMapping("/{url}")
    public ResponseEntity<byte[]> getImage(@PathVariable(value = "url") String url) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageService.readImage(url));
    }
}
