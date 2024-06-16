package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

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
    public ResponseEntity<PostCreateResponseDTO> addPost(@RequestHeader(value = "Authorization") String header,
                                                         @RequestBody PostCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.postCreate(jwtUtil.getId(header), dto));
    }

    @PostMapping("/like")
    public void addPostLike() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/count")
    public void plusView() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/recent")
    public void addrecentPost() {
        throw new UnsupportedOperationException();
    }

    @PatchMapping("/{post-id}")
    public void updatePost(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{post-id}")
    public void deletePost(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/like/{post-id}")
    public void deletePostLike(@PathVariable(value = "post-id") Long postId) {
        throw new UnsupportedOperationException();
    }

}
