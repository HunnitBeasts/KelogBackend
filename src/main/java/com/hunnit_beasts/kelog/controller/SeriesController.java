package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @GetMapping("/{post-id}")
    public void readSeries(@PathVariable(value = "post-id") Long postId) {}

    @PostMapping
    public void addSeries() {}

    @PostMapping("/post")
    public void addSeriesPost() {}

    @PutMapping("/{post-id}")
    public void updateSeries(@PathVariable(value = "post-id") Long postId) {}

    @DeleteMapping("/{post-id}")
    public void deleteSeries(@PathVariable(value = "post-id") Long postId) {}

    @DeleteMapping("/post/{series-id}/{post-id}")
    public void deleteSeriesPost(@PathVariable(value = "series-id") Long seriesId, @PathVariable(value = "post-id") Long postId) {}
}
