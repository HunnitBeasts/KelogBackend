package com.hunnit_beasts.kelog.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CreateFollowAlarmTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AlarmJpaRepository alarmJpaRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    private Long userId;
    private String token;
    private Long followedUserId;

    @BeforeEach
    void setUp(){

        this.transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(status -> {
            UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                    .userId("testUserId")
                    .password("testPassword")
                    .nickname("testNickname")
                    .briefIntro("testBriefIntro")
                    .email("testEmail")
                    .build();

            userId = authService.signUp(userDto).getId();

            UserCreateRequestDTO followeeUserDTO = UserCreateRequestDTO.builder()
                    .userId("testUserId1")
                    .password("testPassword1")
                    .nickname("testNickname1")
                    .briefIntro("testBriefIntro1")
                    .email("testEmail1")
                    .build();

            followedUserId = authService.signUp(followeeUserDTO).getId();

            CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                    .id(this.userId)
                    .userId("testUserId")
                    .password("testPassword")
                    .userType(UserType.USER)
                    .build();

            token = "Bearer " + jwtUtil.createToken(userInfoDTO);

            return null;
        });

    }
    @Test
    @DisplayName("팔로우 알람 테스트")
    void followAlarm() throws Exception {
        FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                .followee(followedUserId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(followIngRequestDTO);

        mockMvc.perform(post("/following")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(jsonContent));


        await().atMost(10, SECONDS).untilAsserted(() -> {
            Boolean check = alarmJpaRepository.existsByUser_IdAndTargetIdAndAlarmType(followedUserId, userId, AlarmType.FOLLOW);
            Assertions.assertThat(check).isTrue();
        });
    }
    @AfterEach
    void tearDown() {
        transactionTemplate.execute(status -> {
            // 알람 삭제
            alarmJpaRepository.deleteAll();

            // 팔로워 삭제
            authService.withDraw(followedUserId);

            // 사용자 삭제
            authService.withDraw(userId);

            return null;
        });
    }
}
