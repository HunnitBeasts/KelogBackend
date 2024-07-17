package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.request.SocialUpdateRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.SocialType;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class SocialUpdateTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long userId;
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

        CustomUserInfoDTO customUserInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(customUserInfoDTO);
    }

    @Test
    @DisplayName("소셜 업데이트")
    void socialUpdate() throws Exception {
        List<SocialInfos> socialInfos = new ArrayList<>();

        socialInfos.add(new SocialInfos("testUrl 0", SocialType.GITHUB));
        socialInfos.add(new SocialInfos("testUrl 1", SocialType.FACEBOOK));
        socialInfos.add(new SocialInfos("testUrl 2", SocialType.INSTAGRAM));

        SocialUpdateRequestDTO dto = SocialUpdateRequestDTO.builder()
                .socials(socialInfos)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/social/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.socials").isArray())
                .andExpect(jsonPath("$.socials", hasSize(3)))
                .andExpect(jsonPath("$.socials[0].url").value("testUrl 0"))
                .andExpect(jsonPath("$.socials[0].socialType").value(SocialType.GITHUB.name()))
                .andExpect(jsonPath("$.socials[1].url").value("testUrl 2"))
                .andExpect(jsonPath("$.socials[1].socialType").value(SocialType.INSTAGRAM.name()))
                .andExpect(jsonPath("$.socials[2].url").value("testUrl 1"))
                .andExpect(jsonPath("$.socials[2].socialType").value(SocialType.FACEBOOK.name()))
                .andReturn();
    }

    @Test
    @DisplayName("소셜 있던거 수정 및 삭제")
    void socialCreateDelete() throws Exception {
        List<SocialInfos> socialInfos = new ArrayList<>();

        socialInfos.add(new SocialInfos("testUrl 0", SocialType.GITHUB));
        socialInfos.add(new SocialInfos("testUrl 1", SocialType.FACEBOOK));
        socialInfos.add(new SocialInfos("testUrl 2", SocialType.INSTAGRAM));
        socialInfos.add(new SocialInfos("", SocialType.EMAIL));
        socialInfos.add(new SocialInfos("", SocialType.HOMEPAGE));

        userService.socialUpdate(userId,socialInfos);

        List<SocialInfos> updateSocialInfos = new ArrayList<>();

        updateSocialInfos.add(new SocialInfos("testUrl 0 update", SocialType.GITHUB));
        updateSocialInfos.add(new SocialInfos("", SocialType.FACEBOOK));
        updateSocialInfos.add(new SocialInfos("", SocialType.INSTAGRAM));
        updateSocialInfos.add(new SocialInfos("", SocialType.EMAIL));
        updateSocialInfos.add(new SocialInfos("testUrl 4", SocialType.HOMEPAGE));

        SocialUpdateRequestDTO updateDto = SocialUpdateRequestDTO.builder()
                .socials(updateSocialInfos)
                .build();

        String jsonContent = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(patch("/social/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.socials").isArray())
                .andExpect(jsonPath("$.socials", hasSize(2)))
                .andExpect(jsonPath("$.socials[0].url").value("testUrl 0 update"))
                .andExpect(jsonPath("$.socials[0].socialType").value(SocialType.GITHUB.name()))
                .andExpect(jsonPath("$.socials[1].url").value("testUrl 4"))
                .andExpect(jsonPath("$.socials[1].socialType").value(SocialType.HOMEPAGE.name()))
                .andReturn();
    }
}
