package com.hunnit_beasts.kelog.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.service.UserService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CreatePostAlarmTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AlarmJpaRepository alarmJpaRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    private Long userId;
    private String token;
    private Long followUserId;
    private Long postId;
    @Autowired
    private PostJpaRepository postJpaRepository;

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

            //포스트 알람을 위한 팔로우유저, user(userId 가진) 를 팔로우함
            UserCreateRequestDTO followUserDTO = UserCreateRequestDTO.builder()
                    .userId("testUserId1")
                    .password("testPassword1")
                    .nickname("testNickname1")
                    .briefIntro("testBriefIntro1")
                    .email("testEmail1")
                    .build();

            followUserId = authService.signUp(followUserDTO).getId();

            CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                    .id(this.userId)
                    .userId("testUserId")
                    .password("testPassword")
                    .userType(UserType.USER)
                    .build();

            token = "Bearer " + jwtUtil.createToken(userInfoDTO);

            FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                    .followee(userId)
                    .build();

            userService.following(followUserId,followIngRequestDTO);

            return null;
        });

    }
    @Test
    @DisplayName("게시물 알람 테스트")
    void tagCreatePostAlarm() throws Exception {

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

        MvcResult result = mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        PostCreateResponseDTO postDto = objectMapper.readValue(result.getResponse().getContentAsString(), PostCreateResponseDTO.class);
        this.postId = postDto.getId();

        await().atMost(10, SECONDS).untilAsserted(() -> {
            Boolean check = alarmJpaRepository.existsByUser_IdAndTargetIdAndAlarmType(followUserId, postId, AlarmType.SUBSCRIBE);
            Assertions.assertThat(check).isTrue();
        });
    }
    @AfterEach
    void tearDown() {
        transactionTemplate.execute(status -> {
            // 알람 삭제
            alarmJpaRepository.deleteAll();

            // 게시물 삭제
            postService.postDelete(postId);

            // 사용자 삭제
            authService.withDraw(userId);

            //팔로워 삭제
            authService.withDraw(followUserId);

            return null;
        });
    }
}
