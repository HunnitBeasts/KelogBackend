package com.hunnit_beasts.kelog.alarm;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.service.CommentService;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Log4j2
class DeleteAllAlarmTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    AlarmJpaRepository alarmJpaRepository;

    @Autowired
    LikedPostJpaRepository likedPostJpaRepository;

    @Autowired
    AlarmService alarmService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    private Long userId;
    private String token;
    private Long followUserId;
    private Long postId;
    private Long commentId;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    void setUp() {

        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        this.userId = authService.signUp(userDto).getId();

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

        //팔로잉
        FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                .followee(userId)
                .build();

        userService.following(followUserId, followIngRequestDTO);

        //포스트 생성
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

        //댓글 달기
        CommentCreateRequestDTO commentDto = CommentCreateRequestDTO.builder()
                .postId(postId)
                .content("testCommentContent")
                .build();

        commentId = commentService.commentCreate(followUserId, commentDto).getId();

        //게시물 좋아요
        PostLikeRequestDTO likeDto = PostLikeRequestDTO.builder()
                .postId(postId)
                .build();

        postService.addPostLike(followUserId, likeDto);

        User user = userJpaRepository.findById(userId).orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        //좋아요 알람
        alarmJpaRepository.save(new Alarm(user, postId, AlarmType.LIKE));

        //게시물 알람
        alarmJpaRepository.save(new Alarm(user, postId, AlarmType.SUBSCRIBE));

        //팔로우 알람
        alarmJpaRepository.save(new Alarm(user, followUserId, AlarmType.FOLLOW));

        //댓글 알람
        alarmJpaRepository.save(new Alarm(user, commentId, AlarmType.COMMENT));

    }

    @Test
    @DisplayName("알람 전체 삭제 테스트")
    void deleteAlarm() throws Exception {

        mockMvc.perform(delete("/alarm/{user-id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertThat(alarmService.readAlarm(userId)).isEmpty();

    }

}
