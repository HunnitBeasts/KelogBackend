package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.response.image.ImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageResponseDTO uploadFile(MultipartFile file);
}
