package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    @PostMapping
    public void uploadImage() {}

    @GetMapping("/{url}")
    public void getImage(@PathVariable(value = "url") String url) {}
}
