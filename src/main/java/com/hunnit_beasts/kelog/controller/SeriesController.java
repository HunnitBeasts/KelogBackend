package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @GetMapping("/{postId}")
    public void readSeries(@PathVariable(value = "postId") int postId) {}

    @PostMapping()
    public void addSeries() {}

    @PostMapping("/series/post")
    public void addSeriesPost() {}

    @PutMapping("/{postId}")
    public void updateSeries(@PathVariable(value = "postId") int postId) {}

    @DeleteMapping("/{postId}")
    public void deleteSeries(@PathVariable(value = "postId") int postId) {}

    @DeleteMapping("/series/post/{seriesId}/{postId}")
    public void deleteSeriesPost(@PathVariable(value = "seriesId") int seriesId, @PathVariable(value = "postId") int postId) {}
}
