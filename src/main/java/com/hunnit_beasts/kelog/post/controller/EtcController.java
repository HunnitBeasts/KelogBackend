package com.hunnit_beasts.kelog.post.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EtcController {

    @GetMapping("/view-cnt/{post-id}")
    public void viewCount(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }
}
