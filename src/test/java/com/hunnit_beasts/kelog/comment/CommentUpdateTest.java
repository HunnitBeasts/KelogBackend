package com.hunnit_beasts.kelog.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.request.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.comment.service.CommentService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class CommentUpdateTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    private Long commentId;
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

        Long userId = authService.signUp(userDto).getId();

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

        UserCreateRequestDTO commentWriter = UserCreateRequestDTO.builder()
                .userId("testCommentWriterId")
                .password("testCommentWriterPassword")
                .nickname("testCommentWriterNickname")
                .briefIntro("testBriefIntro")
                .email("testCommentWriterEmail")
                .build();

        Long writerId = authService.signUp(commentWriter).getId();

        CommentCreateRequestDTO contentDto = CommentCreateRequestDTO.builder()
                .postId(postId)
                .content("testCommentContent")
                .build();

        commentId = commentService.commentCreate(writerId, contentDto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(writerId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        CommentUpdateRequestDTO dto = CommentUpdateRequestDTO.builder()
                .content("updateContent")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/comments/{comment-id}",commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(commentId))
                .andExpect(jsonPath(".content").value("updateContent"))
                .andExpect(jsonPath("modDate").isString());
    }
}
