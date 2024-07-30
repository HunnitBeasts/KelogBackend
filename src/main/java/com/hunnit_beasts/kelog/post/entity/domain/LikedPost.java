package com.hunnit_beasts.kelog.post.entity.domain;

import com.hunnit_beasts.kelog.common.entity.superclass.RegEntity;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikedPost extends RegEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public LikedPost(User user, Post post){
        this.user = user;
        this.post = post;
    }

}
