package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.RecentViewCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.user.jwt.JwtUtil;
import com.hunnit_beasts.kelog.user.service.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class RecentPostViewTest {

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
    private Long postId;
    private String token;

    @BeforeEach
    void setUp(){
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
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);
    }

    @Test
    @DisplayName("최근 본 게시물 등록")
    void recentViewPost() throws Exception {

        RecentViewCreateRequestDTO dto = RecentViewCreateRequestDTO.builder()
                .postId(postId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/posts/recent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.postId").value(postId))
                .andReturn();
    }
}
