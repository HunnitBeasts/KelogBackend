package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/{postId}")
    public void readPost(@PathVariable(value = "postId") Long postId) {}

    @GetMapping
    public void getPostList() {}

    @PostMapping
    public void addPost() {}

    @PostMapping("/like")
    public void addPostLike() {}

    @PostMapping("/count")
    public void plusView() {}

    @PostMapping("/recent")
    public void addrecentPost() {}

    @PatchMapping("/{postId}")
    public void updatePost(@PathVariable(value = "postId") Long postId) {}

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable(value = "postId") Long postId) {}

    @DeleteMapping("/like/{postId}")
    public void deletePostLike(@PathVariable(value = "postId") Long postId) {}

}
