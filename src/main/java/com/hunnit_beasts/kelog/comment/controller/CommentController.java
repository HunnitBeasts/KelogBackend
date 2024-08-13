package com.hunnit_beasts.kelog.comment.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.request.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.response.*;
import com.hunnit_beasts.kelog.comment.service.CommentService;
import com.hunnit_beasts.kelog.common.event.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/{comment-id}")
    public ResponseEntity<CommentReadResponseDTO> readComment(@PathVariable(value = "comment-id") Long commentId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.commentRead(commentId));
    }

    @GetMapping("/{post-id}/list")
    public ResponseEntity<CommentListReadResponseDTO> commentList(@PathVariable(value = "post-id") Long postId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.commentListRead(postId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentCreateResponseDTO> addComment(Authentication authentication,
                                                               @RequestBody CommentCreateRequestDTO dto) {

        CommentCreateResponseDTO responseDTO =
                commentService.commentCreate(authenticatedService.getId(authentication), dto);
        eventPublisher.publishEvent(new CommentEvent(responseDTO));
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
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
