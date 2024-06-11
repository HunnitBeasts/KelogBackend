package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    @GetMapping("/{user-id}")
    public void tagList(@PathVariable(value = "user-id") Long userId) {}

    @PostMapping
    public void addTag() {}

    @GetMapping
    public void allTags() {}

}
