package com.hunnit_beasts.kelog.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import com.hunnit_beasts.kelog.comment.repository.CommentJpaRepository;
import com.hunnit_beasts.kelog.comment.service.CommentService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Log4j2
class CommentReplyReadTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentJpaRepository commentJpaRepository;

    @Autowired
    JwtUtil jwtUtil;

    private Long commentId;
    private Long commentId1;
    private Long commentId2;
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

        Long commentWriterId = authService.signUp(commentWriter).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(commentWriterId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        CommentCreateRequestDTO dto = CommentCreateRequestDTO.builder()
                .postId(postId)
                .content("testCommentContent")
                .build();

        commentId = commentService.commentCreate(userId,dto).getId();

        CommentCreateRequestDTO reCommentDto1 = CommentCreateRequestDTO.builder()
                .postId(postId)
                .commentId(commentId)
                .content("testCommentContent1")
                .build();

        commentId1 = commentService.commentCreate(commentWriterId,reCommentDto1).getId();

        CommentCreateRequestDTO reCommentDto2 = CommentCreateRequestDTO.builder()
                .postId(postId)
                .commentId(commentId)
                .content("testCommentContent2")
                .build();

        commentId2 = commentService.commentCreate(commentWriterId,reCommentDto2).getId();

        Comment comment = commentJpaRepository.findById(commentId).get();
        Comment comment1 = commentJpaRepository.findById(commentId1).get();
        Comment comment2 = commentJpaRepository.findById(commentId2).get();

        log.info(comment.getChildReComments());
    }

    @Test
    @DisplayName("대댓글 읽기")
    void readReComment() throws Exception {

        mockMvc.perform(get("/comments/{comment-id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("replyCount").value(2L));

        mockMvc.perform(get("/comments/{comment-id}/reply", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("infos[0].id").isNumber())
                .andExpect(jsonPath("infos[0].thumbImage").isString())
                .andExpect(jsonPath("infos[0].nickname").value("testCommentWriterNickname"))
                .andExpect(jsonPath("infos[0].regDate").isString())
                .andExpect(jsonPath("infos[0].content").value("testCommentContent1"))
                .andExpect(jsonPath("infos[0].content").value("testCommentContent2"))
                .andExpect(jsonPath("infos[0].replyCount").value(0L))
                .andExpect(jsonPath("infos").isArray())
                .andExpect(jsonPath("infos",hasSize(2)));
    }
}
