package com.hunnit_beasts.kelog.dto.response.user;

import com.hunnit_beasts.kelog.entity.domain.Follower;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowIngResponseDTO {
    private Long follower;
    private Long followee;

    public FollowIngResponseDTO(Follower follower){
        this.follower = follower.getId().getFollower();
        this.followee = follower.getId().getFollowee();
    }
}
