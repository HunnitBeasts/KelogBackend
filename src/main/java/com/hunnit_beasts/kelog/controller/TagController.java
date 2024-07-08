package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.aop.Identification;
import com.hunnit_beasts.kelog.dto.request.tag.TagCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.tag.TagCreateResponseDTO;
import com.hunnit_beasts.kelog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
                .body(tagService.createTag(dto));
    }

    @GetMapping
    public void allTags() {
        throw new UnsupportedOperationException();
    }

}
