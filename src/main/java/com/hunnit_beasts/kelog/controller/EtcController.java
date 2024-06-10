package com.hunnit_beasts.kelog.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EtcController {

    @GetMapping("/view-cnt/{postId}")
    public void viewCount(@PathVariable(value = "postId") int postId) {}
}
