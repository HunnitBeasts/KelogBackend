package com.hunnit_beasts.kelog.postassist.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.postassist.dto.request.PostAddRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostAddResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostPopResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;
import com.hunnit_beasts.kelog.postassist.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final AuthenticatedService authenticatedService;

    @GetMapping("/{post-id}")
    public void readSeries(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserSeriesResponseDTO> readUserSeries(@PathVariable(value = "user-id") Long userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(seriesService.readUserSeries(userId));
    }

    @PostMapping
    public ResponseEntity<SeriesCreateResponseDTO> addSeries(@RequestBody SeriesCreateRequestDTO dto,
                                                             Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(seriesService.createSeries(authenticatedService.getId(authentication),dto));
    }

    @PostMapping("/post")
    @Identification
    public ResponseEntity<PostAddResponseDTO> addPostToSeries(@RequestBody PostAddRequestDTO params,
                                                              Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(seriesService.seriesAddPost(params.getPostId(), params.getSeriesId()));
    }

    @PutMapping("/{post-id}")
    public void updateSeries(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{series-id}")
    @Identification
    public ResponseEntity<Long> deleteSeries(@PathVariable(value = "series-id") Long seriesId,
                                             Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(seriesService.deleteSeries(seriesId));
    }

    @DeleteMapping("/post/{series-id}/{post-id}")
    @Identification
    public ResponseEntity<PostPopResponseDTO> deleteSeriesPost(@PathVariable(value = "series-id") Long seriesId,
                                                               @PathVariable(value = "post-id") Long postId,
                                                               Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(seriesService.seriesPopPost(postId,seriesId));
    }
}
