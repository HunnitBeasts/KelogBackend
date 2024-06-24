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

    @MapsId("following")
    @ManyToOne
    @JoinColumn(name = "following")
    private User following;

    @MapsId("followed")
    @ManyToOne
    @JoinColumn(name = "followed")
    private User followed;

    public Follower(User following, User followed){
        this.id = new FollowerId(following.getId(),following.getId());
        this.following = following;
        this.followed = followed;
    }

}
