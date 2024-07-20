package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostUpdateRequestDTO;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class PostUpdateTest {

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

    private Long userId;
    private String token;
    private Long postId;

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

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        // 게시물 생성
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
    }

    @Test
    @DisplayName("게시물 수정 테스트 - 태그 수정 포함")
    void updatePostWithTags() throws Exception {
        PostUpdateRequestDTO dto = PostUpdateRequestDTO.builder()
                .title("updatedTitle")
                .type(PostType.NORMAL)
                .thumbImage("updatedThumbImage")
                .isPublic(false)
                .shortContent("updatedShortContent")
                .url("updatedUrl")
                .content("updatedContent")
                .tags(Arrays.asList("tag2", "tag3", "newTag"))
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/posts/{post-id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("updatedTitle"))
                .andExpect(jsonPath("$.type").value("NORMAL"))
                .andExpect(jsonPath("$.thumbImage").value("updatedThumbImage"))
                .andExpect(jsonPath("$.isPublic").value(false))
                .andExpect(jsonPath("$.shortContent").value("updatedShortContent"))
                .andExpect(jsonPath("$.url").value("updatedUrl"))
                .andExpect(jsonPath("$.content").value("updatedContent"))
                .andExpect(jsonPath("$.modDate").isString())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(3)))
                .andExpect(jsonPath("$.tags", containsInAnyOrder("tag2", "tag3", "newTag")))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 부분 수정 테스트 - 일부 필드만 수정")
    void partialUpdatePost() throws Exception {
        PostUpdateRequestDTO dto = PostUpdateRequestDTO.builder()
                .title("updatedTitle")
                .isPublic(false)
                .tags(Arrays.asList("tag1", "newTag"))
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/posts/{post-id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("updatedTitle"))
                .andExpect(jsonPath("$.type").value("NORMAL"))
                .andExpect(jsonPath("$.thumbImage").value("originalThumbImage"))
                .andExpect(jsonPath("$.isPublic").value(false))
                .andExpect(jsonPath("$.shortContent").value("originalShortContent"))
                .andExpect(jsonPath("$.url").value("originalUrl"))
                .andExpect(jsonPath("$.content").value("originalContent"))
                .andExpect(jsonPath("$.modDate").isString())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags", containsInAnyOrder("tag1", "newTag")))
                .andReturn();
    }
}
