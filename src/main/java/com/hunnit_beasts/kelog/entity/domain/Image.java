package com.hunnit_beasts.kelog.entity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {

    @Id
    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private Long fileSize;

    public Image(MultipartFile file, Path path){
        this.fileType = file.getContentType();
        this.originalFileName = file.getOriginalFilename();
        this.filePath = path.toString();
        this.fileSize = file.getSize();
    }
}
