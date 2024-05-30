package com.hunnit_beasts.kelog.entity.domain;


import com.hunnit_beasts.kelog.entity.compositekey.RecentPostId;
import jakarta.persistence.*;

@Entity
public class RecentPost {

    @EmbeddedId
    private RecentPostId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("postId")
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
