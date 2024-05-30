package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.superclass.RegEntity;
import jakarta.persistence.*;

@Entity
public class LikedPost extends RegEntity {

    @EmbeddedId
    private LikedPostId likedPostId;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("postId")
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
