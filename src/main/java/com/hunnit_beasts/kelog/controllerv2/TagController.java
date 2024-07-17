package com.hunnit_beasts.kelog.controllerv2;

import com.hunnit_beasts.kelog.postassist.dto.response.TagCreateResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/tags")
public class TagController {

    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagCreateResponseDTO dto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public ResponseEntity<?> getAllTags() {
        throw new UnsupportedOperationException();
    }
}
