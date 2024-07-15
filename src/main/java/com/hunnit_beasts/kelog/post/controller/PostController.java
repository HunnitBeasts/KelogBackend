package com.hunnit_beasts.kelog.post.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.post.dto.request.*;
import com.hunnit_beasts.kelog.post.dto.response.*;
import com.hunnit_beasts.kelog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final PostService postService;
    private final AuthenticatedService authenticatedService;

    @GetMapping("/{post-id}")
    public void readPost(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public void getPostList() {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostCreateResponseDTO> addPost(@RequestBody PostCreateRequestDTO dto,
                                                         Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.postCreate(authenticatedService.getId(authentication), dto));
    }

    @PostMapping("/like")
    public ResponseEntity<PostLikeResponseDTO> addPostLike(@RequestBody PostLikeRequestDTO dto,
                                                           Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.addPostLike(authenticatedService.getId(authentication), dto));
    }

    @PostMapping("/count")
    public ResponseEntity<PostViewCntResponseDTO> plusView(@RequestBody PostViewCntRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.plusViewCnt(dto.getPostId()));
    }

    @PostMapping("/recent")
    public ResponseEntity<RecentViewCreateResponseDTO> addRecentPost(@RequestBody RecentViewCreateRequestDTO dto,
                                                                     Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.recentViewAdd(authenticatedService.getId(authentication),dto.getPostId()));
    }

    @PatchMapping("/{post-id}")
    @Identification
    public ResponseEntity<PostUpdateResponseDTO> updatePost(@PathVariable(value = "post-id") Long postId,
                                                            Authentication authentication,
                                                            @RequestBody PostUpdateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.postUpdate(postId,dto));
    }

    @DeleteMapping("/{post-id}")
    @PreAuthorize("hasRole('USER')")
    @Identification
    public ResponseEntity<Long> deletePost(@PathVariable(value = "post-id") Long postId,
                                           Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(postService.postDelete(postId));
    }

    @DeleteMapping("/like/{post-id}")
    public ResponseEntity<PostLikeResponseDTO> deletePostLike(@PathVariable(value = "post-id") Long postId,
                                                              Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.deletePostLike(authenticatedService.getId(authentication), postId));
    }

}
