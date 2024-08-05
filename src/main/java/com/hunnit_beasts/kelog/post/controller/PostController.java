package com.hunnit_beasts.kelog.post.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.common.event.PostEvent;
import com.hunnit_beasts.kelog.common.event.PostLikeEvent;
import com.hunnit_beasts.kelog.post.dto.request.*;
import com.hunnit_beasts.kelog.post.dto.response.*;
import com.hunnit_beasts.kelog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/{post-id}")
    public ResponseEntity<PostReadResponseDTO> readPost(@PathVariable(value = "post-id") Long postId,
                                                        Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.postRead(postId,authenticatedService.getId(authentication)));
    }

    @GetMapping("/{user-id}/{url}")
    public ResponseEntity<PostReadResponseDTO> urlReadPost(@PathVariable(value = "user-id") String userId,
                                                           @PathVariable(value = "url") String url,
                                                           Authentication authentication) {
        Long postId = postService.getPostId(userId,url);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.postRead(postId,authenticatedService.getId(authentication)));
    }

    @GetMapping
    public ResponseEntity<PostPageResponseDTO> getPostList(@RequestParam(value = "tag-name", required = false) String tagName,
                                                           @RequestParam(value = "sort", defaultValue = "reg-date", required = false) String sort,
                                                           @RequestParam(value = "page", defaultValue = "1", required = false) Long page,
                                                           @RequestParam(value = "size", defaultValue = "20", required = false) Long size,
                                                           @RequestParam(value = "search", required = false) String search,
                                                           @RequestParam(value = "user-id", required = false) Long userId) {
        PostPageRequestDTO dto = new PostPageRequestDTO(tagName,sort,page,size,search,userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.readPostList(dto));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostCreateResponseDTO> addPost(@RequestBody PostCreateRequestDTO dto,
                                                         Authentication authentication) {
        PostCreateResponseDTO responseDTO =
                postService.postCreate(authenticatedService.getId(authentication), dto);

        eventPublisher.publishEvent(new PostEvent(responseDTO));

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    @PostMapping("/like")
    public ResponseEntity<PostLikeResponseDTO> addPostLike(@RequestBody PostLikeRequestDTO dto,
                                                           Authentication authentication) {
        PostLikeResponseDTO responseDTO =
                postService.addPostLike(authenticatedService.getId(authentication), dto);

        eventPublisher.publishEvent(new PostLikeEvent(responseDTO));

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
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
