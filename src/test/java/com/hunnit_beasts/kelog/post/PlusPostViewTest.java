package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.post.PostViewCntRequestDTO;
import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.enumeration.types.PostType;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.service.AuthService;
import com.hunnit_beasts.kelog.service.PostService;
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
public class PlusPostViewTest {

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
    }

    @Test
    @DisplayName("조회수 올리기")
    void plusPostViewCnt() throws Exception {
        PostViewCntRequestDTO dto = PostViewCntRequestDTO.builder()
                .postId(postId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/posts/count")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".totalViewCut").value(1));

        mockMvc.perform(post("/posts/count")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".totalViewCut").value(2));

    }
}
