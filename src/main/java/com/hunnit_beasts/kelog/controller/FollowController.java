package com.hunnit_beasts.kelog.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/following")
public class FollowController {

    @PostMapping
    public void addFollow() {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{follower-id}/{followee-id}")
    public void deleteFollow(@PathVariable(value = "follower-id") Long followerId,
                             @PathVariable(value = "followee-id") Long followeeId) {
        throw new UnsupportedOperationException();
    }

}
