package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    @GetMapping("/{user-id}")
    public void tagList(@PathVariable(value = "user-id") Long userId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public void addTag() {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public void allTags() {
        throw new UnsupportedOperationException();
    }

}
