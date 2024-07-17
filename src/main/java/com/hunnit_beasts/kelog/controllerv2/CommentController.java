package com.hunnit_beasts.kelog.controllerv2;

import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/comments")
public class CommentController {

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment dto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable Long commentId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody Comment dto) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getPostComments(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }
}
