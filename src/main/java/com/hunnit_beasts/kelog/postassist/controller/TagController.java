package com.hunnit_beasts.kelog.postassist.controller;

import com.hunnit_beasts.kelog.postassist.dto.response.TagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/{user-id}")
    public ResponseEntity<TagsResponseDTO> tagList(@PathVariable(value = "user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tagService.userTags(userId));
    }

    @GetMapping
    public ResponseEntity<TagsResponseDTO> allTags() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tagService.allTags());
    }

}
