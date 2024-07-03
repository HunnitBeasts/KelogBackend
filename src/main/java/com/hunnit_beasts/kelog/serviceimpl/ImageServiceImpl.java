package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.config.ImageConfig;
import com.hunnit_beasts.kelog.dto.response.image.ImageResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Image;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
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
            throw new IllegalArgumentException(ErrorCode.FILE_SIZE_OVER_ERROR.getCode());

        if (!isImageFile(mimeType))
            throw new IllegalArgumentException(ErrorCode.NOT_FILE_TYPE_ERROR.getCode());

        String newFileName = generateUniqueFileName(originalFileName);
        Path filePath = Paths.get( imageConfig.getFileDirectory() + File.separator + newFileName);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorCode.FILE_UPLOAD_FAILURE_ERROR.getCode()+"File upload exception. " + Arrays.toString(e.getStackTrace()));
        }

        Image saveImage = imageJpaRepository.save(new Image(file,filePath,newFileName));

        return new ImageResponseDTO(saveImage);
    }

    @Override
    public byte[] readImage(String url) throws IOException {
        Image callImageData = imageJpaRepository.findById(url)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_IMAGE_DATA_ERROR.getCode()));
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
