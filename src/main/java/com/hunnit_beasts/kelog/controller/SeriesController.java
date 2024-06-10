package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @GetMapping("/{postId}")
    public void readSeries(@PathVariable(value = "postId") Long postId) {}

    @PostMapping
    public void addSeries() {}

    @PostMapping("/post")
    public void addSeriesPost() {}

    @PutMapping("/{postId}")
    public void updateSeries(@PathVariable(value = "postId") Long postId) {}

    @DeleteMapping("/{postId}")
    public void deleteSeries(@PathVariable(value = "postId") Long postId) {}

    @DeleteMapping("/post/{seriesId}/{postId}")
    public void deleteSeriesPost(@PathVariable(value = "seriesId") Long seriesId, @PathVariable(value = "postId") Long postId) {}
}
