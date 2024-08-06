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
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.entity.domain.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CheckAllAlarmTest {

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
    UserJpaRepository userJpaRepository;

    private Long userId;
    private Long followUserId;
    private String token;
    private String followUserToken;

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

        this.followUserId = authService.signUp(followUserDTO).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(this.userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        CustomUserInfoDTO followUserInfoDTO = CustomUserInfoDTO.builder()
                .id(this.followUserId)
                .userId("testUserId1")
                .password("testPassword1")
                .userType(UserType.USER)
                .build();

        followUserToken = "Bearer " + jwtUtil.createToken(followUserInfoDTO);

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

        Long postId = postService.postCreate(userId, postDto).getId();

        //댓글 달기
        CommentCreateRequestDTO commentDto = CommentCreateRequestDTO.builder()
                .postId(postId)
                .content("testCommentContent")
                .build();

        Long commentId = commentService.commentCreate(followUserId, commentDto).getId();

        //게시물 좋아요
        PostLikeRequestDTO likeDto = PostLikeRequestDTO.builder()
                .postId(postId)
                .build();

        postService.addPostLike(userId, likeDto);

        User user = userJpaRepository.findById(userId).orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        User follower = userJpaRepository.findById(followUserId).orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        LikedPost likedPost = likedPostJpaRepository.findByPost_IdAndUser_Id(postId,userId);
        //좋아요 알람
        alarmJpaRepository.save(new Alarm(user, likedPost.getId(), AlarmType.LIKE));

        //게시물 알람
        alarmJpaRepository.save(new Alarm(follower, postId, AlarmType.SUBSCRIBE));

        //팔로우 알람
        alarmJpaRepository.save(new Alarm(user, followUserId, AlarmType.FOLLOW));

        //댓글 알람
        alarmJpaRepository.save(new Alarm(user, commentId, AlarmType.COMMENT));

    }

    @Test
    void allCheck() throws Exception {

        mockMvc.perform(patch("/alarm/{user-id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].check").value("true"))
                .andExpect(jsonPath("$[1].check").value("true"))
                .andExpect(jsonPath("$[2].check").value("true"));

        mockMvc.perform(patch("/alarm/{user-id}", followUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", followUserToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].check").value("true"));

    }
}
