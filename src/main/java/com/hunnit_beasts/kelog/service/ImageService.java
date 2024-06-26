package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.response.image.ImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageResponseDTO uploadFile(MultipartFile file);
    byte[] readImage(String url) throws IOException;
}
