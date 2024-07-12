package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.entity.domain.RecentPost;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecentViewCreateResponseDTO {
    private Long userId;
    private Long postId;

    public RecentViewCreateResponseDTO(RecentPost recentPost){
        this.userId = recentPost.getUser().getId();
        this.postId = recentPost.getPost().getId();
    }
}
