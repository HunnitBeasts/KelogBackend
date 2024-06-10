package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @GetMapping("/{userId}")
    public void alarm(@PathVariable Long userId) {}
}
