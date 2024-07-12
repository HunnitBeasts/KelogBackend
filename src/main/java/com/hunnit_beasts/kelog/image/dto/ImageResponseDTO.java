package com.hunnit_beasts.kelog.image.dto;

import com.hunnit_beasts.kelog.image.domain.Image;
import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class ImageResponseDTO {
    private final String url;
    private final String fileType;
    private final String originalFileName;
    private final Path filePath;
    private final Long fileSize;

    public ImageResponseDTO(Image image){
        this.url = image.getUrl();
        this.fileType = image.getFileType();
        this.originalFileName = image.getOriginalFileName();
        this.filePath = Paths.get(image.getFilePath());
        this.fileSize = image.getFileSize();
    }
}
