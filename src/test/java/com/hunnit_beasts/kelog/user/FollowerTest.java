package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.user.dto.convert.FollowerInfos;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowerReadResponseDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class FollowerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long mainUserId;
    private final List<Long> otherUserIds = new ArrayList<>();
    private String token;

    @BeforeEach
    void setup() {
        UserCreateRequestDTO mainUserDto = UserCreateRequestDTO.builder()
                .userId("mainUserId")
                .password("mainPassword")
                .nickname("mainNickname")
                .briefIntro("mainBriefIntro")
                .email("mainEmail")
                .build();

        mainUserId = authService.signUp(mainUserDto).getId();

        for (int i = 0; i < 5; i++) {
            UserCreateRequestDTO otherUserDto = UserCreateRequestDTO.builder()
                    .userId("otherUserId" + i)
                    .password("otherPassword" + i)
                    .nickname("otherNickname" + i)
                    .briefIntro("otherBriefIntro" + i)
                    .email("otherEmail" + i)
                    .build();

            Long otherUserId = authService.signUp(otherUserDto).getId();
            otherUserIds.add(otherUserId);

            FollowIngRequestDTO followMainUserDto = FollowIngRequestDTO.builder()
                    .followee(mainUserId)
                    .build();
            userService.following(otherUserId, followMainUserDto);

            if (i % 2 != 0) {
                FollowIngRequestDTO followOtherUserDto = FollowIngRequestDTO.builder()
                        .followee(otherUserId)
                        .build();
                userService.following(mainUserId, followOtherUserDto);
            }
        }

        CustomUserInfoDTO customUserInfoDTO = CustomUserInfoDTO.builder()
                .id(mainUserId)
                .userId("mainUserId")
                .password("mainPassword")
                .userType(UserType.USER)
                .build();

        token = jwtUtil.createToken(customUserInfoDTO);
    }

    @Test
    @DisplayName("내가 팔로우한 사람들 조회 성공")
    void readFollowerYourSuccess() throws Exception {
        mockMvc.perform(get("/following/{user-id}/your", mainUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followerCount").value(2))
                .andExpect(jsonPath("$.userInfos", hasSize(2)))
                .andExpect(jsonPath("$.userInfos[0].isFollow").value(true))
                .andExpect(jsonPath("$.userInfos[1].isFollow").value(true))
                .andReturn();
    }

    @Test
    @DisplayName("나를 팔로우한 사람들 조회 성공")
    void readFolloweeMeSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/following/{user-id}/me", mainUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followerCount").value(5))
                .andExpect(jsonPath("$.userInfos", hasSize(5)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        FollowerReadResponseDTO response = objectMapper.readValue(content, FollowerReadResponseDTO.class);

        int followCount = 0;
        for (FollowerInfos info : response.getUserInfos())
            if (info.getIsFollow())
                followCount++;
        assertEquals(2, followCount);
    }
}