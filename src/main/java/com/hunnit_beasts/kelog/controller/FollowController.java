package com.hunnit_beasts.kelog.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/following")
public class FollowController {

    @PostMapping
    public void addFollow() {}

    @DeleteMapping("/{followerId}/{followeeId}")
    public void deleteFollow(@PathVariable(value = "followerId") Long followerId, @PathVariable(value = "followeeId") Long followeeId) {}

}
