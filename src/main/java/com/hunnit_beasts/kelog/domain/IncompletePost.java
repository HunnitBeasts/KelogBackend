package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.IncompletePostId;
import jakarta.persistence.*;

@Entity
public class IncompletePost {

    @EmbeddedId
    private IncompletePostId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("postId")
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
