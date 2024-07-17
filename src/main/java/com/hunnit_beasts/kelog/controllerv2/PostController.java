package com.hunnit_beasts.kelog.controllerv2;

import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostUpdateRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/posts")
public class PostController {

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequestDTO dto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDTO postDto) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{postId}/view")
    public ResponseEntity<?> incrementViewCount(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{postId}/view-count")
    public ResponseEntity<?> getViewCount(@PathVariable Long postId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/recent")
    public ResponseEntity<?> addRecentPost(@RequestBody Long postId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/users/{userId}/{url}")
    public ResponseEntity<?> getUserPost(@PathVariable Long userId, @PathVariable String url) {
        throw new UnsupportedOperationException();
    }
}
