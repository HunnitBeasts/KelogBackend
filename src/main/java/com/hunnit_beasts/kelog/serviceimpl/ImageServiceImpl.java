package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.config.ImageConfig;
import com.hunnit_beasts.kelog.dto.response.image.ImageResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Image;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.handler.exception.FileUploadException;
import com.hunnit_beasts.kelog.repository.jpa.ImageJpaRepository;
import com.hunnit_beasts.kelog.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageJpaRepository imageJpaRepository;
    private final ImageConfig imageConfig;

    @Value("${upload.max-size}")
    private Long maxSize;

    @Override
    public ImageResponseDTO uploadFile(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String mimeType = file.getContentType();

        if (file.getSize() > maxSize)
            throw new IllegalArgumentException("10MB 이하 파일만 업로드 할 수 있습니다.");

        if (!isImageFile(mimeType))
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");

        String newFileName = generateUniqueFileName(originalFileName);
        Path filePath = Paths.get( imageConfig.getFileDirectory() + File.separator + newFileName);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new FileUploadException("File upload exception. " + Arrays.toString(e.getStackTrace()));
        }

        Image saveImage = imageJpaRepository.save(new Image(file,filePath,newFileName));

        return new ImageResponseDTO(saveImage);
    }

    @Override
    public byte[] readImage(String url) throws IOException {
        Image callImageData = imageJpaRepository.findById(url)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_IMAGE_DATA_ERROR.getMessage()));
        return Files.readAllBytes(Paths.get(callImageData.getFilePath()));
    }

    private String generateUniqueFileName(String originalFileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String uuid = UUID.randomUUID().toString();
        String timeStamp = dateFormat.format(new Date());
        return timeStamp + uuid + originalFileName;
    }

    public boolean isImageFile(String mimeType) {
        return mimeType != null && mimeType.startsWith("image");
    }
}
