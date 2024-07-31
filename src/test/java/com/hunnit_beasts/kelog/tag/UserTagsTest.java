package com.hunnit_beasts.kelog.tag;

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
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class UserTagsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long userId;
    private String token;

    @BeforeEach
    void setUp() {
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(userDto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        createPost("Post1", Arrays.asList("tag1", "tag2"));
        createPost("Post2", Arrays.asList("tag2", "tag3"));
        createPost("Post3", Arrays.asList("tag3", "tag4", "tag5"));
    }

    private void createPost(String title, List<String> tags) {
        PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                .title(title)
                .type(PostType.NORMAL)
                .thumbImage("thumbImage")
                .isPublic(true)
                .shortContent("shortContent")
                .url("url")
                .content("content")
                .tags(tags)
                .build();

        postService.postCreate(userId, postDto);
    }

    @Test
    @DisplayName("사용자의 태그별 게시물 수 조회 테스트")
    void getUserTagsCountTest() throws Exception {
        mockMvc.perform(get("/tags/{user-id}", userId)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postCount ").value(3))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(5)))
                .andExpect(jsonPath("$.tags[*].tagName", containsInAnyOrder("tag1", "tag2", "tag3", "tag4", "tag5")))
                .andExpect(jsonPath("$.tags[?(@.tagName == 'tag1')].tagCount").value(1))
                .andExpect(jsonPath("$.tags[?(@.tagName == 'tag2')].tagCount").value(2))
                .andExpect(jsonPath("$.tags[?(@.tagName == 'tag3')].tagCount").value(2))
                .andExpect(jsonPath("$.tags[?(@.tagName == 'tag4')].tagCount").value(1))
                .andExpect(jsonPath("$.tags[?(@.tagName == 'tag5')].tagCount").value(1))
                .andDo(print());
    }
}
