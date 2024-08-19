package com.hunnit_beasts.kelog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.enumeration.SocialType;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
class MyInfoReadTest {
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

        Long userId = authService.signUp(dto).getId();

        UserCreateRequestDTO followUserDTO = UserCreateRequestDTO.builder()
                .userId("testUserId1")
                .password("testPassword1")
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .email("testEmail1")
                .build();

        Long followUserId = authService.signUp(followUserDTO).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        User user = userJpaRepository.findById(userId).orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        //팔로우 알람
        alarmJpaRepository.save(new Alarm(user, followUserId, AlarmType.FOLLOW));

        //소셜 업데이트
        List<SocialInfos> socialInfos = new ArrayList<>();

        socialInfos.add(new SocialInfos("testUrl 0", SocialType.GITHUB));
        socialInfos.add(new SocialInfos("testUrl 1", SocialType.FACEBOOK));
        socialInfos.add(new SocialInfos("testUrl 2", SocialType.INSTAGRAM));

        userService.socialUpdate(userId, socialInfos);
    }

    @Test
    void myInfoReadTest() throws Exception {

        mockMvc.perform(get("/users/me")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("testNickname"))
                .andExpect(jsonPath("nickname").isString())
                .andExpect(jsonPath("briefIntro").value("testBriefIntro"))
                .andExpect(jsonPath("email").value("testEmail"))
                .andExpect(jsonPath("emailSetting").isBoolean())
                .andExpect(jsonPath("alarmSetting").isBoolean())
                .andExpect(jsonPath("kelogName").isString())
                .andExpect(jsonPath("alarmCount").value(1L))
                .andExpect(jsonPath("socials").isArray())
                .andExpect(jsonPath("socials", hasSize(3)));

    }
}
