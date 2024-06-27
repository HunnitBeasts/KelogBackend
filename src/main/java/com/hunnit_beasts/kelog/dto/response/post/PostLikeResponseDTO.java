package com.hunnit_beasts.kelog.dto.response.post;

import com.hunnit_beasts.kelog.entity.domain.LikedPost;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponseDTO {
    private Long userId;
    private Long postId;

    public PostLikeResponseDTO(LikedPost likedPost){
        this.userId = likedPost.getLikedPostId().getUserId();
        this.postId = likedPost.getLikedPostId().getPostId();
    }
}
