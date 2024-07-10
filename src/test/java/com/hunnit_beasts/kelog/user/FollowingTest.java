package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.user.jwt.JwtUtil;
import com.hunnit_beasts.kelog.user.service.AuthService;
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

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class FollowingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long userId;
    private Long followedUserId;
    private String token;

    @BeforeEach
    void setup() {
        UserCreateRequestDTO signUpDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(signUpDto).getId();

        UserCreateRequestDTO followUserDTO = UserCreateRequestDTO.builder()
                .userId("testUserId1")
                .password("testPassword1")
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .email("testEmail1")
                .build();

        followedUserId = authService.signUp(followUserDTO).getId();

        CustomUserInfoDTO customUserInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = jwtUtil.createToken(customUserInfoDTO);
    }

    @Test
    @DisplayName("팔로우 기능 성공")
    void followSuccess() throws Exception {
        FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                .followee(followedUserId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(followIngRequestDTO);

        mockMvc.perform(post("/following")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.follower").value(userId))
                .andExpect(jsonPath("$.followee").value(followedUserId))
                .andReturn();
    }
}
