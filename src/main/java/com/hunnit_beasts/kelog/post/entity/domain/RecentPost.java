package com.hunnit_beasts.kelog.post.entity.domain;


import com.hunnit_beasts.kelog.common.entity.superclass.RegEntity;
import com.hunnit_beasts.kelog.post.entity.compositekey.RecentPostId;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "recent_post", indexes = {
        @Index(name = "idx_reg_date", columnList = "reg_date")
})
public class RecentPost extends RegEntity {

    @EmbeddedId
    private RecentPostId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public RecentPost(User user, Post post){
        this.id = new RecentPostId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
    }

}
