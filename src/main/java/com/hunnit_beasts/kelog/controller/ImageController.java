package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    @PostMapping
    public void uploadImage() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{url}")
    public void getImage(@PathVariable(value = "url") String url) {
        throw new UnsupportedOperationException();
    }
}
