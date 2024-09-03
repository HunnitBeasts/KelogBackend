package com.hunnit_beasts.kelog.user;

import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class DuplicateCheckTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setup() {
        UserCreateRequestDTO signUpDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        authService.signUp(signUpDto);
    }

    @Test
    @DisplayName("유저 아이디 중복")
    void duplicateUserId() throws Exception {
        mockMvc.perform(get("/users/duplicate")
                        .param("keyword", "testUserId")
                        .param("keywordType", "USER_ID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();
    }

    @Test
    @DisplayName("유저 아이디 중복 아님")
    void notDuplicateUserId() throws Exception {
        mockMvc.perform(get("/users/duplicate")
                        .param("keyword", "testUserId111")
                        .param("keywordType", "USER_ID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andReturn();
    }

    @Test
    @DisplayName("유저 이메일 중복")
    void duplicateEmail() throws Exception {
        mockMvc.perform(get("/users/duplicate")
                        .param("keyword", "testEmail")
                        .param("keywordType", "EMAIL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andReturn();
    }

    @Test
    @DisplayName("유저 이메일 중복 아님")
    void notDuplicateEmail() throws Exception {
        mockMvc.perform(get("/users/duplicate")
                        .param("keyword", "testEmail111")
                        .param("keywordType", "EMAIL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andReturn();
    }
}
