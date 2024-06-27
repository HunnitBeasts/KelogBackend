package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.entity.superclass.RegEntity;
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
