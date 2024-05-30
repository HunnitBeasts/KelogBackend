package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.entity.superclass.RegEntity;
import jakarta.persistence.*;

@Entity
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

}
