package com.hunnit_beasts.kelog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @GetMapping("/{post-id}")
    public void readSeries(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public void addSeries() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/post")
    public void addSeriesPost() {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{post-id}")
    public void updateSeries(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{post-id}")
    public void deleteSeries(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/post/{series-id}/{post-id}")
    public void deleteSeriesPost(@PathVariable(value = "series-id") Long seriesId,
                                 @PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }
}
