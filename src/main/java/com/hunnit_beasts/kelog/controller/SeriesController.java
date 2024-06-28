package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.dto.request.post.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.post.SeriesCreateResponseDTO;
import com.hunnit_beasts.kelog.service.AuthenticatedService;
import com.hunnit_beasts.kelog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final PostService postService;
    private final AuthenticatedService authenticatedService;

    @GetMapping("/{post-id}")
    public void readSeries(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public ResponseEntity<SeriesCreateResponseDTO> addSeries(@RequestBody SeriesCreateRequestDTO dto,
                                                             Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.createSeries(authenticatedService.getId(authentication),dto));
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
