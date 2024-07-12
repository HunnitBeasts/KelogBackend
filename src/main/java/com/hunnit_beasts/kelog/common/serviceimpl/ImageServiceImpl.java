package com.hunnit_beasts.kelog.common.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.service.ImageService;
import com.hunnit_beasts.kelog.image.config.ImageConfig;
import com.hunnit_beasts.kelog.image.domain.Image;
import com.hunnit_beasts.kelog.image.dto.ImageResponseDTO;
import com.hunnit_beasts.kelog.image.repository.ImageJpaRepository;
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
            throw new ExpectException(ErrorCode.FILE_SIZE_OVER_ERROR);

        if (!isImageFile(mimeType))
            throw new ExpectException(ErrorCode.NOT_FILE_TYPE_ERROR);

        String newFileName = generateUniqueFileName(originalFileName);
        Path filePath = Paths.get( imageConfig.getFileDirectory() + File.separator + newFileName);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new ExpectException(ErrorCode.FILE_UPLOAD_FAILURE_ERROR);
        }

        Image saveImage = imageJpaRepository.save(new Image(file,filePath,newFileName));

        return new ImageResponseDTO(saveImage);
    }

    @Override
    public byte[] readImage(String url) throws IOException {
        Image callImageData = imageJpaRepository.findById(url)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_IMAGE_DATA_ERROR));
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
