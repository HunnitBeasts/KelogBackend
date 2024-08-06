package com.hunnit_beasts.kelog.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.transaction.Transactional;
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

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CreateLikeAlarmTest {
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
    LikedPostJpaRepository likedPostJpaRepository;

    private Long userId;
    private Long postId;
    private String token;

    @BeforeEach
    @Transactional
    void setUp() {

        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(userDto).getId();

        PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        postId = postService.postCreate(userId, postDto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(this.userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

    }

    @Test
    @DisplayName("게시물 좋아요 알람 테스트")
    void postLikeAlarm() throws Exception {
        PostLikeRequestDTO dto = PostLikeRequestDTO.builder()
                .postId(postId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);


        mockMvc.perform(post("/posts/like")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent));

        await().atMost(10, SECONDS).untilAsserted(() -> {
            LikedPost likedPost = likedPostJpaRepository.findByPost_IdAndUser_Id(postId, userId);
            Assertions.assertThat(likedPost).isNotNull();  // likedPost가 null이 아닌지 확인
            Boolean result = alarmJpaRepository.existsByUser_IdAndTargetIdAndAlarmType(userId, likedPost.getId(), AlarmType.LIKE);
            Assertions.assertThat(result).isTrue();
        });


    }

    @AfterEach
    @Transactional
    void tearDown() {

        // 알람 삭제
        alarmJpaRepository.deleteAll();

        // 좋아요 삭제
        likedPostJpaRepository.deleteAll();

        // 게시물 삭제
        postService.postDelete(postId);

        // 사용자 삭제
        authService.withDraw(userId);

    }
}
