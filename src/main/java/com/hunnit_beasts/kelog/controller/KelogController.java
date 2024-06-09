package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class KelogController {

    @GetMapping("/alarm")
    public void alarm() {}

    @GetMapping("/view-cnt/{postId}")
    public void viewCount(@PathVariable(value = "postId") int postId) {}

    @PostMapping("/images")
    public void uploadImage() {}

    @GetMapping("/images/{url}")
    public void getImage(@PathVariable(value = "url") String url) {}
}
