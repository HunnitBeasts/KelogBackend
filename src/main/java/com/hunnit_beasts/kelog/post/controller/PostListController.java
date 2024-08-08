package com.hunnit_beasts.kelog.post.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserRelatedPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.enumeration.TrendType;
import com.hunnit_beasts.kelog.post.service.PostListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostListController {

    private final PostListService postListService;
    private final AuthenticatedService authenticatedService;

    @GetMapping
    public ResponseEntity<PostPageResponseDTO> getPostList(
            @RequestParam(value = "tag-name", required = false) String tagName,
            @RequestParam(value = "sort", defaultValue = "reg-date", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "1", required = false) Long page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Long size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "user-id", required = false) Long userId) {
        PostPageRequestDTO dto = new PostPageRequestDTO(tagName,sort,page,size,search,userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postListService.readPostList(dto));
    }

    @GetMapping("/{user-id}/like")
    @Identification
    public ResponseEntity<PostPageResponseDTO> userLikesPosts(
            @PathVariable(name = "user-id") Long userId,
            @RequestParam(value = "sort", defaultValue = "reg-date", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "1", required = false) Long page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Long size,
            @RequestParam(value = "search", required = false) String search,
            Authentication authentication){
        UserRelatedPostRequestDTO dto =
                new UserRelatedPostRequestDTO(authenticatedService.getId(authentication),sort,page,size,search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postListService.readLikePosts(dto));
    }

    @GetMapping("/trending/{trend-type}")
    public ResponseEntity<PostPageResponseDTO> trendPosts(
            @PathVariable(name = "trend-type") TrendType type,
            @RequestParam(value = "sort", defaultValue = "reg-date", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "1", required = false) Long page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Long size,
            @RequestParam(value = "search", required = false) String search){
        TrendPostRequestDTO dto = new TrendPostRequestDTO(size,page,search,sort,type);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postListService.trendPosts(dto));
    }

    @GetMapping("/{user-id}/reading")
    public ResponseEntity<PostPageResponseDTO> recentPosts(
            @PathVariable(name = "user-id") Long userId,
            @RequestParam(value = "sort", defaultValue = "reg-date", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "1", required = false) Long page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Long size,
            @RequestParam(value = "search", required = false) String search,
            Authentication authentication){
        UserRelatedPostRequestDTO dto =
                new UserRelatedPostRequestDTO(authenticatedService.getId(authentication),sort,page,size,search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postListService.readRecentPosts(dto));
    }
}
