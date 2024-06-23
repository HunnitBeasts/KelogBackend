package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @GetMapping("/{comment-id}")
    public void readComment(@PathVariable(value = "comment-id") Long commentId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{post-id}")
    public void commentList(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentCreateResponseDTO> addComment(@RequestHeader(value = "Authorization") String token,
                                                               @RequestBody CommentCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.commentCreate(jwtUtil.getId(token), dto));
    }

    @PutMapping("/{comment-id}")
    public void updateComment(@PathVariable(value = "comment-id") Long commentId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{comment-id}")
    public void deleteComment(@PathVariable(value = "comment-id") Long commentId) {
        throw new UnsupportedOperationException();
    }

}