package com.hunnit_beasts.kelog.postassist.entity.domain.compositekey;

import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikedPostId implements Serializable {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;

    public LikedPostId(User user, Post post){
        this.userId = user.getId();
        this.postId = post.getId();
    }
}
