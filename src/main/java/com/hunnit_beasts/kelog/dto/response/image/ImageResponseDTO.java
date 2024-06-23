package com.hunnit_beasts.kelog.dto.response.image;

import com.hunnit_beasts.kelog.entity.domain.Image;
import lombok.Getter;

@Getter
public class ImageResponseDTO {
    private final String fileType;
    private final String originalFileName;
    private final String filePath;
    private final Long fileSize;

    public ImageResponseDTO(Image image){
        this.fileType = image.getFileType();
        this.originalFileName = image.getOriginalFileName();
        this.filePath = image.getFilePath();
        this.fileSize = image.getFileSize();
    }
}
