package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/{post-id}")
    public void readPost(@PathVariable(value = "post-id") Long postId) {}

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

    @PatchMapping("/{post-id}")
    public void updatePost(@PathVariable(value = "post-id") Long postId) {}

    @DeleteMapping("/{post-id}")
    public void deletePost(@PathVariable(value = "post-id") Long postId) {}

    @DeleteMapping("/like/{post-id}")
    public void deletePostLike(@PathVariable(value = "post-id") Long postId) {}

}
