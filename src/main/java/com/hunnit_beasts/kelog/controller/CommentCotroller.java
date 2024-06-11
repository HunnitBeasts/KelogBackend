package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentCotroller {

    @GetMapping("/{comment-id}")
    public void readComment(@PathVariable(value = "comment-id") Long commentId) {}

    @GetMapping("/{post-id}")
    public void commentList(@PathVariable(value = "post-id") Long postId) {}

    @PostMapping
    public void addComment() {}

    @PutMapping("/{comment-id}")
    public void updateComment(@PathVariable(value = "comment-id") Long commentId) {}

    @DeleteMapping("/{comment-id}")
    public void deleteComment(@PathVariable(value = "comment-id") Long commentId) {}

}
