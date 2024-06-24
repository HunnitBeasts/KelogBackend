package com.hunnit_beasts.kelog.dto.response.user;

import com.hunnit_beasts.kelog.entity.domain.Follower;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowIngResponseDTO {
    private Long following;
    private Long followed;

    public FollowIngResponseDTO(Follower follower){
        this.following = follower.getId().getFollowing();
        this.followed = follower.getId().getFollowed();
    }
}
