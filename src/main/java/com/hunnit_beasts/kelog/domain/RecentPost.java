package com.hunnit_beasts.kelog.domain;


import com.hunnit_beasts.kelog.compositekey.RecentPostId;
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
