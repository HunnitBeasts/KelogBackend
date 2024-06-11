package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
public class SocialController {

    @PostMapping
    public void addSocial() {}

    @DeleteMapping("/{user-id}")
    public void deleteSocial(@PathVariable(value = "user-id") Long userId) {}
}
