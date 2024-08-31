package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.request.UserInfoUpdateRequestDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserInfoUpdateTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    AlarmJpaRepository alarmJpaRepository;

    @Autowired
    JwtUtil jwtUtil;

    private Long userId;
    private String token;
    private final List<SocialInfos> socialInfos = new ArrayList<>();

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(dto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        socialInfos.add(new SocialInfos("testUrl 0", SocialType.GITHUB));
        socialInfos.add(new SocialInfos("testUrl 1", SocialType.FACEBOOK));
        socialInfos.add(new SocialInfos("testUrl 2", SocialType.INSTAGRAM));

    }

    @Test
    @DisplayName("유저 정보 업데이트 테스트")
    void userInfoUpdateTest() throws Exception {

        mockMvc.perform(get("/users/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("testNickname"))
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("kelogName").isString())
                .andExpect(jsonPath("socials", hasSize(0)));

        UserInfoUpdateRequestDTO dto = UserInfoUpdateRequestDTO.builder()
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .thumbImage("testThumbImage1")
                .email("testEmail1")
                .emailSetting(true)
                .alarmSetting(false)
                .kelogName("testKelogName1")
                .soicals(socialInfos)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/users/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("testNickname1"))
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("thumbImage").value("testThumbImage1"))
                .andExpect(jsonPath("email").value("testEmail1"))
                .andExpect(jsonPath("kelogName").value("testKelogName1"))
                .andExpect(jsonPath("emailSetting").value(true))
                .andExpect(jsonPath("alarmSetting").value(false))
                .andExpect(jsonPath("socials").isArray())
                .andExpect(jsonPath("socials", hasSize(3)));

    }

    @Test
    @DisplayName("유저 정보 업데이트 테스트(하나만 변경할때)")
    void userInfoUpdateTest2() throws Exception {

        mockMvc.perform(get("/users/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("testNickname"))
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("kelogName").isString())
                .andExpect(jsonPath("socials", hasSize(0)));

        UserInfoUpdateRequestDTO dto = UserInfoUpdateRequestDTO.builder()
                .nickname("changedNickname")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/users/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("changedNickname"))
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("kelogName").isString())
                .andExpect(jsonPath("socials", hasSize(0)));
    }
}
