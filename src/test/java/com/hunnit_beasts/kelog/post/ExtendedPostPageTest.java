package com.hunnit_beasts.kelog.post;

import com.hunnit_beasts.kelog.KelogApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class ExtendedPostPageTest extends PostPageTest {

    @Test
    @DisplayName("게시물 리스트 조회 - 빈 결과 테스트")
    void getEmptyPostList() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("search", "nonexistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(0)))
                .andExpect(jsonPath("$.posts", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 최대 페이지 크기 테스트")
    void getPostListWithMaxPageSize() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", "1")
                        .param("size", "100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(50)))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 여러 태그 필터링 테스트 -- 여러개 태그를 만들지 모르겠음 일단은 1개 태그만 함")
    void getPostListWithMultipleTags() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("tag-name", "tag0,tag1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 정렬 테스트 (댓글 수)")
    void getPostListSortedByComments() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("sort", "comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.posts[0].commentCount", greaterThanOrEqualTo(1)))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 잘못된 정렬 기준 테스트 -- 코드만 만들었고 아직 적용 안함 필요에 따라 할 것")
    void getPostListWithInvalidSortCriteria() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("sort", "invalid_criteria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
