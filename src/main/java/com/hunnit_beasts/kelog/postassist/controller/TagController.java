package com.hunnit_beasts.kelog.postassist.controller;

import com.hunnit_beasts.kelog.postassist.dto.request.TagCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.AllTagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.TagCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/{user-id}")
    public void tagList(@PathVariable(value = "user-id") Long userId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TagCreateResponseDTO> addTag(
                                                @RequestBody TagCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tagService.createTag(dto.getTagName()));
    }

    @GetMapping
    public ResponseEntity<AllTagsResponseDTO> allTags() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(tagService.allTags());
    }

}
