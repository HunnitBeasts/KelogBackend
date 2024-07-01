package com.hunnit_beasts.kelog.dto.response.post;

import com.hunnit_beasts.kelog.entity.domain.RecentPost;
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
