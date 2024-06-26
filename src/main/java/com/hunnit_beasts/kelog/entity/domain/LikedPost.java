package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.entity.superclass.RegEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public LikedPost(User user, Post post){
        this.likedPostId = new LikedPostId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
    }

}
