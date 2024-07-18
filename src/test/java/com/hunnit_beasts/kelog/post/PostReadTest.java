package com.hunnit_beasts.kelog.post;

import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class PostReadTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    private Long userId;
    private Long postId;
    private String token;

    @BeforeEach
    void setUp() {
        // 사용자 생성
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(userDto).getId();

        // 포스트 생성
        PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                .title("originalTitle")
                .type(PostType.NORMAL)
                .thumbImage("originalThumbImage")
                .isPublic(true)
                .shortContent("originalShortContent")
                .url("originalUrl")
                .content("originalContent")
                .tags(Arrays.asList("tag1", "tag2"))
                .build();

        postId = postService.postCreate(userId, postDto).getId();

        // 토큰 생성
        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);
    }

    @Test
    @DisplayName("로그인한 사용자가 자신의 게시물을 조회")
    void readOwnPost() throws Exception {
        mockMvc.perform(get("/posts/{post-id}", postId)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kelogName").value("testUserId"))
                .andExpect(jsonPath("$.title").value("originalTitle"))
                .andExpect(jsonPath("$.nickname").value("testNickname"))
                .andExpect(jsonPath("$.isFollow").value(false))
                .andExpect(jsonPath("$.isLike").value(false))
                .andExpect(jsonPath("$.likeCount").value(0))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.content").value("originalContent"));
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 게시물을 조회")
    void readPostAsGuest() throws Exception {
        mockMvc.perform(get("/posts/{post-id}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isFollow").value(false))
                .andExpect(jsonPath("$.isLike").value(false));
    }

    @Test
    @DisplayName("다른 사용자의 게시물을 팔로우하고 좋아요 누른 후 조회")
    void readPostWithFollowAndLike() throws Exception {
        // 새로운 사용자 생성
        UserCreateRequestDTO anotherUserDto = UserCreateRequestDTO.builder()
                .userId("anotherUserId")
                .password("anotherPassword")
                .nickname("anotherNickname")
                .briefIntro("anotherBriefIntro")
                .email("anotherEmail")
                .build();

        Long anotherUserId = authService.signUp(anotherUserDto).getId();

        // 팔로우
        FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                .followee(userId)
                .build();

        userService.following(anotherUserId, followIngRequestDTO);

        // 좋아요
        PostLikeRequestDTO postLikeDto = PostLikeRequestDTO.builder()
                .postId(postId)
                .build();

        postService.addPostLike(anotherUserId, postLikeDto);

        // 새로운 사용자의 토큰 생성
        CustomUserInfoDTO anotherUserInfoDTO = CustomUserInfoDTO.builder()
                .id(anotherUserId)
                .userId("anotherUserId")
                .password("anotherPassword")
                .userType(UserType.USER)
                .build();

        String anotherToken = "Bearer " + jwtUtil.createToken(anotherUserInfoDTO);

        mockMvc.perform(get("/posts/{post-id}", postId)
                        .header("Authorization", anotherToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isFollow").value(true))
                .andExpect(jsonPath("$.isLike").value(true))
                .andExpect(jsonPath("$.likeCount").value(1));
    }

    @Test
    @DisplayName("존재하지 않는 게시물 조회")
    void readNonExistentPost() throws Exception {
        mockMvc.perform(get("/posts/{post-id}", 99999L)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("비공개 게시물을 다른 사용자가 조회")
    void readPrivatePostAsOtherUser() throws Exception {
        // 비공개 게시물 생성
        PostCreateRequestDTO privatePostDto = PostCreateRequestDTO.builder()
                .title("privateTitle")
                .type(PostType.NORMAL)
                .thumbImage("privateThumbImage")
                .isPublic(false)
                .shortContent("privateShortContent")
                .url("privateUrl")
                .content("privateContent")
                .tags(List.of("privateTag"))
                .build();

        Long privatePostId = postService.postCreate(userId, privatePostDto).getId();

        // 다른 사용자 생성 및 토큰 발급
        UserCreateRequestDTO otherUserDto = UserCreateRequestDTO.builder()
                .userId("otherUserId")
                .password("otherPassword")
                .nickname("otherNickname")
                .briefIntro("otherBriefIntro")
                .email("otherEmail")
                .build();

        Long otherUserId = authService.signUp(otherUserDto).getId();

        CustomUserInfoDTO otherUserInfoDTO = CustomUserInfoDTO.builder()
                .id(otherUserId)
                .userId("otherUserId")
                .password("otherPassword")
                .userType(UserType.USER)
                .build();

        String otherToken = "Bearer " + jwtUtil.createToken(otherUserInfoDTO);

        mockMvc.perform(get("/posts/{post-id}", privatePostId)
                        .header("Authorization", otherToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}