package com.hunnit_beasts.kelog.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.user.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.service.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class LoginIllegalArgumentExceptionTest {

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        authService.signUp(dto);
    }

    @Test
    @DisplayName("login IllegalArgumentException 예외 테스트")
    void exceptionHandleTest1() throws Exception {

        UserLoginRequestDTO dto = UserLoginRequestDTO.builder()
                .userId("testUserId1") //예외 발생부분
                .password("testPassword")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("errorMessage").value("[ERROR] 유저데이터가 없습니다!"))
                .andExpect(jsonPath("time").isString())
                .andReturn();
    }

}
