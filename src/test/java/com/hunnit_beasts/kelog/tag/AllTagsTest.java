package com.hunnit_beasts.kelog.tag;


import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AllTagsTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        Long userId = authService.signUp(userDto).getId();

        PostCreateRequestDTO dto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .tags(Arrays.asList("사랑에 푹 빠졌나봐", "사랑의 이름표", "추억의 테헤란로", "봉선화 연정", "싫다 싫어"))
                .build();

        postService.postCreate(userId,dto);
    }

    @Test
    @DisplayName("태그 만들기 테스트")
    void createTagTest() throws Exception {
        mockMvc.perform(get("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasSize(5)))
                .andExpect(jsonPath("$.tags[0]").value("봉선화 연정"))
                .andExpect(jsonPath("$.tags[1]").value("사랑에 푹 빠졌나봐"))
                .andExpect(jsonPath("$.tags[2]").value("사랑의 이름표"))
                .andExpect(jsonPath("$.tags[3]").value("싫다 싫어"))
                .andExpect(jsonPath("$.tags[4]").value("추억의 테헤란로"));
    }
}
