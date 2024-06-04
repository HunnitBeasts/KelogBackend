package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class KelogController {

    @PostMapping("/users")
    public void signUp() {}

    @GetMapping("/users")
    public void searchUser(@RequestParam int userId) {}

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam int userId) {}

    @PostMapping("/posts")
    public void addPost() {}

    @GetMapping("/posts")
    public void readPost(@RequestParam int postId) {}

    @PatchMapping("/posts")
    public void updatePost(@RequestParam int postId) {}

    @DeleteMapping("/posts")
    public void deletePost(@RequestParam int postId) {}

    @PostMapping("/comments")
    public void addComment() {}

    @GetMapping("/comments")
    public void readComment(@RequestParam int commentId) {}

    @PutMapping("/comments")
    public void updateComment(@RequestParam int commentId) {}

    @DeleteMapping("/comments")
    public void deleteComment(@RequestParam int commentId) {}

    @GetMapping("/comments")
    public void commentList(@RequestParam int postId) {}

    @PostMapping("/following")
    public void addFollow() {}

    @DeleteMapping("/following")
    public void deleteFollow(@RequestParam int followerId, @RequestParam int followeeId) {}

    @PostMapping("/posts/like")
    public void addPostLike() {}

    @DeleteMapping("/posts/like")
    public void deletePostLike(@RequestParam int postId) {}

    @PostMapping("/series")
    public void addSeries() {}

    @GetMapping("/series")
    public void readSeries(@RequestParam int postId) {}

    @PutMapping("/series")
    public void updateSeries(@RequestParam int postId) {}

    @DeleteMapping("/series")
    public void deleteSeries(@RequestParam int postId) {}

    @PostMapping("/series/post")
    public void addSeriesPost() {}

    @DeleteMapping("/series/post")
    public void deleteSeriesPost(@RequestParam int seriesId, @RequestParam int postId) {}

    @PostMapping("/posts/recent")
    public void addrecentPost() {}

    @PostMapping("/social")
    public void addSocial() {}

    @DeleteMapping("/social")
    public void deleteSocial(@RequestParam int userId) {}

    @PatchMapping("/users")
    public void updateUserInfo(@RequestParam int userId) {}

    @DeleteMapping("/users/{userId}/thumbnail")
    public void deleteThumbnail(@PathVariable int userId){}

    @GetMapping("/tags")
    public void tagList(@RequestParam int userId) {}

    @PostMapping("/tags")
    public void addTag() {}

    @GetMapping("/tags")
    public void allTags() {}

    @GetMapping("/alarm")
    public void alarm() {}

    @GetMapping("/view-cnt")
    public void viewCount(@RequestParam int postId) {}

    @PostMapping("/images")
    public void uploadImage() {}

    @GetMapping("/images")
    public void getImage(@RequestParam String url) {}

    @PostMapping("/posts/count")
    public void plusView() {}

    @GetMapping("/posts")
    public void getPostList() {}
}
