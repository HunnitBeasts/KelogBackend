package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponseDTO {
    private Long userId;
    private Long postId;

    public PostLikeResponseDTO(LikedPost likedPost){
        this.userId = likedPost.getUser().getId();
        this.postId = likedPost.getPost().getId();
    }
}
