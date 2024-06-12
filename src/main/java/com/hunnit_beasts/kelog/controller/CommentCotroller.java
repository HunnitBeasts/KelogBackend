package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentCotroller {

    @GetMapping("/{comment-id}")
    public void readComment(@PathVariable(value = "comment-id") Long commentId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{post-id}")
    public void commentList(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public void addComment() {
        throw new UnsupportedOperationException();
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
