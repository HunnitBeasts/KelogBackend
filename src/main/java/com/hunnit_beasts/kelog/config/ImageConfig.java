package com.hunnit_beasts.kelog.config;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@Log4j2
public class ImageConfig {

    @Value("${upload.directory.window}")
    private String windowUploadDirectory;

    @Value("${upload.directory.linux}")
    private String linuxUploadDirectory;

    @Value("${upload.directory.mac}")
    private String macUploadDirectory;

    @Getter
    public String fileDirectory;

    @PostConstruct
    public void init() throws FileNotFoundException {
        String osName = System.getProperty("os.name").toLowerCase();
        File directory;

        if (osName.contains("win"))
            directory = new File(windowUploadDirectory);
        else if (osName.contains("mac"))
            directory = new File(macUploadDirectory);
        else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"))
            directory = new File(linuxUploadDirectory);
        else
            throw new IllegalArgumentException(ErrorCode.NOT_SUPPORTED_OS_ERROR.getCode()+"Unsupported operating system: " + osName);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created)
                log.info("Directory created successfully: {}", directory.getAbsolutePath());
            else {
                log.error("Failed to create directory: {}", directory.getAbsolutePath());
                throw new FileNotFoundException("Failed to create directory: " + directory.getAbsolutePath());
            }
        } else
            log.info("Directory already exists: {}", directory.getAbsolutePath());

        fileDirectory = directory.getPath();
    }

}
