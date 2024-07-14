package com.hunnit_beasts.kelog.post.controller;


import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.post.dto.response.PostViewCountResponseDTO;
import com.hunnit_beasts.kelog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EtcController {

    private final PostService postService;

    @GetMapping("/view-cnt/{post-id}")
    @Identification
    public ResponseEntity<PostViewCountResponseDTO> viewCount(@PathVariable(value = "post-id") Long postId,
                                                              Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.viewCntInfos(postId));
    }
}
