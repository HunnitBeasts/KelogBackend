package com.hunnit_beasts.kelog.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @GetMapping("/{user-id}")
    public void alarm(@PathVariable(value = "user-id") Long userId) {
        throw new UnsupportedOperationException();
    }

}
