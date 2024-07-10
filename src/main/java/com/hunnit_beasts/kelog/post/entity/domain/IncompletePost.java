package com.hunnit_beasts.kelog.post.entity.domain;

import com.hunnit_beasts.kelog.post.entity.compositekey.IncompletePostId;
import com.hunnit_beasts.kelog.user.entity.domain.User;
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
