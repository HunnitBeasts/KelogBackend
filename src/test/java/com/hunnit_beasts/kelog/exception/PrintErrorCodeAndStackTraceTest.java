package com.hunnit_beasts.kelog.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.enumeration.types.PostType;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PrintErrorCodeAndStackTraceTest {

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
    @DisplayName("에러 코드 및 스택 트레이스 출력 테스트")
    void printErrorCodeStackTrace() throws Exception {

        PostCreateRequestDTO dto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/print-errorcode-stacktrace-exception")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("errorMessage").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("time").isString())
                .andReturn();

    }
}