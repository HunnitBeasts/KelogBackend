package com.hunnit_beasts.kelog.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/following")
public class FollowController {

    @PostMapping()
    public void addFollow() {}

    @DeleteMapping("/{followerId}/{followeeId}")
    public void deleteFollow(@PathVariable(value = "followerId") int followerId, @PathVariable(value = "followeeId") int followeeId) {}

}
