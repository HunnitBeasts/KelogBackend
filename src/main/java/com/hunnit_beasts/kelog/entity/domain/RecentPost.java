package com.hunnit_beasts.kelog.entity.domain;


import com.hunnit_beasts.kelog.entity.compositekey.RecentPostId;
import com.hunnit_beasts.kelog.entity.superclass.RegEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecentPost extends RegEntity {

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

    public RecentPost(User user, Post post){
        this.id = new RecentPostId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
    }

}
