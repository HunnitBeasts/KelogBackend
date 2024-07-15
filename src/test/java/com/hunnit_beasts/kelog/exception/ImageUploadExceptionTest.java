package com.hunnit_beasts.kelog.exception;

import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.manager.ErrorMessageManager;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ImageUploadExceptionTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ErrorMessageManager errorMessageManager;

    private String token;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        Long userId = authService.signUp(userDto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);
    }

    @Test
    @DisplayName("이미지 파일 10MB 초과 시 예외 테스트")
    void imageUploadSizeException() throws Exception {

        String fileName = "dummyfile.txt";
        long size = 10 * 1024 * 1025; //10mb 크기를 초과하는 파일 사이즈 정의

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024 * 1025];

            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = 'A';
            }
            long bytesWritten = 0;
            while (bytesWritten < size) {
                fos.write(buffer);
                bytesWritten += buffer.length;
            }
        }

        Path filePath = Paths.get(fileName);

        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "multipartFile",
                    fileName,
                    "image/jpg",
                    fis);

            mockMvc.perform(multipart("/images")
                            .file(multipartFile)
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .header("Authorization", token)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath("errorMessage").value(errorMessageManager.getMessages(ErrorCode.FILE_SIZE_OVER_ERROR.name())))
                    .andExpect(jsonPath("time").isString());

            Files.delete(filePath);
        }
    }

    @Test
    @DisplayName("이미지 파일이 아닌 파일 업로드 예외 테스트")
    void imageUploadTypeException() throws Exception {

        MockMultipartFile multipartFile = new MockMultipartFile(
                "multipartFile",
                "test.txt",
                "", //오류를 발생시키는 부분. contentType 이 비어있음
                "testImage".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/images")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(jsonPath("errorMessage").value(errorMessageManager.getMessages(ErrorCode.NOT_FILE_TYPE_ERROR.name())))
                .andExpect(jsonPath("time").isString());

    }
}
