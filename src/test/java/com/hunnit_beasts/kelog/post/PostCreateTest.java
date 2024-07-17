package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class PostCreateTest {

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

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(dto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);
    }

    @Test
    @DisplayName("태그 없는 게시물 작성 테스트")
    void nonTagCreatePost() throws Exception {
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

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.type").value("NORMAL"))
                .andExpect(jsonPath("$.thumbImage").value("testThumbImage"))
                .andExpect(jsonPath("$.isPublic").value("true"))
                .andExpect(jsonPath("$.shortContent").value("testShortContent"))
                .andExpect(jsonPath("$.url").value("testUrl"))
                .andExpect(jsonPath("$.content").value("testContent"))
                .andExpect(jsonPath("$.regDate").isString())
                .andExpect(jsonPath("$.modDate").isString())
                .andReturn();
    }

    @Test
    @DisplayName("태그 있는 게시물 작성 테스트")
    void tagCreatePost() throws Exception {
        PostCreateRequestDTO dto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .tags(Arrays.asList("헤이 저기", "그래 자기", "웬 만하면 내게 오지"))
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.type").value("NORMAL"))
                .andExpect(jsonPath("$.thumbImage").value("testThumbImage"))
                .andExpect(jsonPath("$.isPublic").value("true"))
                .andExpect(jsonPath("$.shortContent").value("testShortContent"))
                .andExpect(jsonPath("$.url").value("testUrl"))
                .andExpect(jsonPath("$.content").value("testContent"))
                .andExpect(jsonPath("$.regDate").isString())
                .andExpect(jsonPath("$.modDate").isString())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(3)))
                .andExpect(jsonPath("$.tags[0]").value("그래 자기"))
                .andExpect(jsonPath("$.tags[1]").value("웬 만하면 내게 오지"))
                .andExpect(jsonPath("$.tags[2]").value("헤이 저기"))
                .andReturn();
    }
}
