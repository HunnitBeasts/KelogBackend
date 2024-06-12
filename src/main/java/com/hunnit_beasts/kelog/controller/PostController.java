package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/{post-id}")
    public void readPost(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public void getPostList() {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public void addPost() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/like")
    public void addPostLike() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/count")
    public void plusView() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/recent")
    public void addrecentPost() {
        throw new UnsupportedOperationException();
    }

    @PatchMapping("/{post-id}")
    public void updatePost(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{post-id}")
    public void deletePost(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/like/{post-id}")
    public void deletePostLike(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

}
