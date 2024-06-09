package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentCotroller {

    @GetMapping("/{commentId}")
    public void readComment(@PathVariable(value = "commentId") int commentId) {}

    @GetMapping("/{postId}")
    public void commentList(@PathVariable(value = "postId") int postId) {}

    @PostMapping()
    public void addComment() {}

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable(value = "commentId") int commentId) {}

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable(value = "commentId") int commentId) {}

}
