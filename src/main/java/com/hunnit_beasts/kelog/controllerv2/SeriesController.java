package com.hunnit_beasts.kelog.controllerv2;

import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.Series;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/series")
public class SeriesController {

    @PostMapping
    public ResponseEntity<?> createSeries(@RequestBody SeriesCreateRequestDTO dto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{seriesId}")
    public ResponseEntity<?> getSeries(@PathVariable Long seriesId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{seriesId}")
    public ResponseEntity<?> updateSeries(@PathVariable Long seriesId,
                                          @RequestBody Series seriesDto) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{seriesId}")
    public ResponseEntity<?> deleteSeries(@PathVariable Long seriesId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{seriesId}/posts")
    public ResponseEntity<?> addPostToSeries(@PathVariable Long seriesId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{seriesId}/posts/{postId}")
    public ResponseEntity<?> removePostFromSeries(@PathVariable Long seriesId, @PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }
}
