package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentCotroller {

    @GetMapping("/{commentId}")
    public void readComment(@PathVariable(value = "commentId") Long commentId) {}

    @GetMapping("/{postId}")
    public void commentList(@PathVariable(value = "postId") Long postId) {}

    @PostMapping
    public void addComment() {}

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable(value = "commentId") Long commentId) {}

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable(value = "commentId") Long commentId) {}

}
