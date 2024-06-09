package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
public class SocialController {

    @PostMapping()
    public void addSocial() {}

    @DeleteMapping("/{userId}")
    public void deleteSocial(@PathVariable(value = "userId") int userId) {}
}
