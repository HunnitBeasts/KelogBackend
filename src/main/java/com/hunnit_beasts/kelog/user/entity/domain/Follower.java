package com.hunnit_beasts.kelog.user.entity.domain;

import com.hunnit_beasts.kelog.common.entity.superclass.RegEntity;
import com.hunnit_beasts.kelog.user.entity.compositekey.FollowerId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follower extends RegEntity {

    @EmbeddedId
    private FollowerId id;

    @MapsId("follower")
    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @MapsId("followee")
    @ManyToOne
    @JoinColumn(name = "followee")
    private User followee;

    public Follower(User follower, User followee){
        this.id = new FollowerId(follower.getId(), follower.getId());
        this.follower = follower;
        this.followee = followee;
    }

}
