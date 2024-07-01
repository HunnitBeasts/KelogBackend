package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.aop.Identification;
import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.comment.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentDeleteResponseDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentUpdateResponseDTO;
import com.hunnit_beasts.kelog.service.AuthenticatedService;
import com.hunnit_beasts.kelog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final AuthenticatedService authenticatedService;

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
    public ResponseEntity<CommentCreateResponseDTO> addComment(Authentication authentication,
                                                               @RequestBody CommentCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.commentCreate(authenticatedService.getId(authentication), dto));
    }

    @PutMapping("/{comment-id}")
    @Identification
    public ResponseEntity<CommentUpdateResponseDTO> updateComment(@PathVariable(value = "comment-id") Long commentId,
                                                                  Authentication authentication,
                                                                  @RequestBody CommentUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.commentUpdate(commentId,dto));
    }

    @DeleteMapping("/{comment-id}")
    @Identification
    public ResponseEntity<CommentDeleteResponseDTO> deleteComment(@PathVariable(value = "comment-id") Long commentId,
                                                                  Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(commentService.commentDelete(commentId));
    }

}
