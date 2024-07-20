package com.hunnit_beasts.kelog.post;

import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = KelogApplication.class)
@AutoConfigureMockMvc
@Transactional
class PostABTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    private Long myUserId;
    private Long yourUserId;
    private String myUserToken;
    private String yourUserToken;

    @BeforeEach
    void setUp() {
        UserCreateRequestDTO userDto1 = UserCreateRequestDTO.builder()
                .userId("testUser1")
                .password("password")
                .nickname("nickname1")
                .email("test1@example.com")
                .build();

        myUserId = authService.signUp(userDto1).getId();

        UserCreateRequestDTO userDto2 = UserCreateRequestDTO.builder()
                .userId("testUser2")
                .password("password")
                .nickname("nickname2")
                .email("test2@example.com")
                .build();

        yourUserId = authService.signUp(userDto2).getId();

        myUserToken = "Bearer " + jwtUtil.createToken(new CustomUserInfoDTO(myUserId, "testUser1", "password", UserType.USER));
        yourUserToken = "Bearer " + jwtUtil.createToken(new CustomUserInfoDTO(yourUserId, "testUser2", "password", UserType.USER));
    }

    @Test
    @DisplayName("특정 사용자의 게시물 읽기 - 다음/이전 게시물 포함")
    void readPostWithNextAndPrevious() throws Exception {
        Long post1Id = createPost(myUserId, "myUser Post first", "url first");
        Long post2Id = createPost(myUserId, "myUser Post second", "url second");
        Long post3Id = createPost(myUserId, "myUser Post third", "url third");
        Long postUser2Id = createPost(yourUserId, "yourUser Post", "url4");

        mockMvc.perform(get("/posts/{post-id}", post2Id)
                        .header("Authorization", myUserToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("myUser Post second"))
                .andExpect(jsonPath("$.afterPostInfo.title").value("myUser Post third"))
                .andExpect(jsonPath("$.beforePostInfo.title").value("myUser Post first"));
    }

    @Test
    @DisplayName("특정 사용자의 첫 번째 게시물 읽기 - 이전 게시물 없음")
    void readFirstPostWithoutPrevious() throws Exception {
        Long post1Id = createPost(myUserId, "myUser Post first", "url first");
        Long post2Id = createPost(myUserId, "myUser Post second", "url second");

        mockMvc.perform(get("/posts/{post-id}", post1Id)
                        .header("Authorization", myUserToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("myUser Post first"))
                .andExpect(jsonPath("$.afterPostInfo.title").value("myUser Post second"))
                .andExpect(jsonPath("$.beforePostInfo").doesNotExist());
    }

    @Test
    @DisplayName("특정 사용자의 마지막 게시물 읽기 - 다음 게시물 없음")
    void readLastPostWithoutNext() throws Exception {
        Long post1Id = createPost(myUserId, "myUser Post first", "url first");
        Long post2Id = createPost(myUserId, "myUser Post second", "url second");

        mockMvc.perform(get("/posts/{post-id}", post2Id)
                        .header("Authorization", myUserToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("myUser Post second"))
                .andExpect(jsonPath("$.beforePostInfo.title").value("myUser Post first"))
                .andExpect(jsonPath("$.afterPostInfo").doesNotExist());
    }

    @Test
    @DisplayName("로그인하지 않은 사용자의 게시물 읽기")
    void readPostAsGuest() throws Exception {
        Long postId = createPost(myUserId, "Public Post", "public-url");

        mockMvc.perform(get("/posts/{post-id}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Public Post"))
                .andExpect(jsonPath("$.isFollow").value(false))
                .andExpect(jsonPath("$.isLike").value(false));
    }

    @Test
    @DisplayName("다른 사용자의 게시물 읽기")
    void readPostAsOtherUser() throws Exception {
        Long postId = createPost(myUserId, "myUser Post", "user1-url");

        mockMvc.perform(get("/posts/{post-id}", postId)
                        .header("Authorization", yourUserToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("myUser Post"))
                .andExpect(jsonPath("$.isFollow").value(false))
                .andExpect(jsonPath("$.isLike").value(false));
    }

    private Long createPost(Long userId, String title, String url) {
        PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                .title(title)
                .type(PostType.NORMAL)
                .thumbImage("thumbImage")
                .isPublic(true)
                .shortContent("shortContent")
                .url(url)
                .content("content")
                .tags(Arrays.asList("tag1", "tag2"))
                .build();

        return postService.postCreate(userId, postDto).getId();
    }
}