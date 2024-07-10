package com.hunnit_beasts.kelog.image;

import com.hunnit_beasts.kelog.common.service.ImageService;
import com.hunnit_beasts.kelog.user.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.user.jwt.JwtUtil;
import com.hunnit_beasts.kelog.user.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Log4j2
class ImageReadTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ImageService imageService;

    @Autowired
    JwtUtil jwtUtil;

    private String token;
    private String url;

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

        MockMultipartFile multipartFile = new MockMultipartFile(
                "multipartFile",
                "test.txt",
                "image/jpg",
                "testImage".getBytes(StandardCharsets.UTF_8) );

        url = imageService.uploadFile(multipartFile).getUrl();
    }

    @Test
    @DisplayName("이미지 읽어오기")
    void imageRead() throws Exception {
        MvcResult result = mockMvc.perform(get("/images/{url}",url)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        log.info("result : {}", result.getResponse().getContentAsString());
    }
}
