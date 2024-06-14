package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import jakarta.transaction.Transactional;
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
public class SignUpTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail@test.com")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath(".userId").value("testUserId"))
                .andExpect(jsonPath(".nickname").value("testNickname"))
                .andExpect(jsonPath(".thumbImage").value(""))
                .andExpect(jsonPath(".briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath(".email").value("testEmail@test.com"))
                .andExpect(jsonPath(".emailSetting").value(false))
                .andExpect(jsonPath(".alarmSetting").value(false))
                .andExpect(jsonPath(".userType").value("USER"))
                .andExpect(jsonPath(".kelogName").value("testUserId"))
                .andExpect(jsonPath(".userIntro").value(""))
                .andExpect(jsonPath("regDate").isString())
                .andExpect(jsonPath("modDate").isString())
                .andReturn();
    }
}
