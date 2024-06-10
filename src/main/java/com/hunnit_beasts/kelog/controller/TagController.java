package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    @GetMapping("/{userId}")
    public void tagList(@PathVariable(value = "userId") Long userId) {}

    @PostMapping
    public void addTag() {}

    @GetMapping
    public void allTags() {}

}
