package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.SocialType;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserInfoReadTest {

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
    UserJpaRepository userJpaRepository;

    @Autowired
    JwtUtil jwtUtil;

    private Long userId;
    private String token;

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

        UserCreateRequestDTO currentUserDTO = UserCreateRequestDTO.builder()
                .userId("testUserId1")
                .password("testPassword1")
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .email("testEmail1")
                .build();

        Long currentUserId = authService.signUp(currentUserDTO).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(currentUserId)
                .userId("testUserId1")
                .password("testPassword1")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                .followee(userId)
                .build();

        userService.following(currentUserId, followIngRequestDTO);

        //소셜 업데이트
        List<SocialInfos> socialInfos = new ArrayList<>();

        socialInfos.add(new SocialInfos("testUrl 0", SocialType.GITHUB));
        socialInfos.add(new SocialInfos("testUrl 1", SocialType.FACEBOOK));
        socialInfos.add(new SocialInfos("testUrl 2", SocialType.INSTAGRAM));

        userService.socialUpdate(userId, socialInfos);
    }

    @Test
    @DisplayName("로그인 한 경우 유저 정보 조회 테스트")
    void userInfoReadTest1() throws Exception {

        mockMvc.perform(get("/users/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("testNickname"))
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("thumbImage").isString())
                .andExpect(jsonPath("kelogName").isString())
                .andExpect(jsonPath("followersCount").value(1L))
                .andExpect(jsonPath("followingsCount").value(0L))
                .andExpect(jsonPath("isFollow").value(true))
                .andExpect(jsonPath("socials").isArray())
                .andExpect(jsonPath("socials", hasSize(3)));

    }

    @Test
    @DisplayName("로그인 하지 않은 경우 유저 정보 조회 테스트")
    void userInfoReadTest2() throws Exception {

        mockMvc.perform(get("/users/{user-id}",userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("testNickname"))
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("thumbImage").isString())
                .andExpect(jsonPath("kelogName").isString())
                .andExpect(jsonPath("followersCount").value(1L))
                .andExpect(jsonPath("followingsCount").value(0L))
                .andExpect(jsonPath("isFollow").value(false))
                .andExpect(jsonPath("socials").isArray())
                .andExpect(jsonPath("socials", hasSize(3)));

    }
}
