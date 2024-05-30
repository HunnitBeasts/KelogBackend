package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.FollowerId;
import com.hunnit_beasts.kelog.superclass.RegEntity;
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
