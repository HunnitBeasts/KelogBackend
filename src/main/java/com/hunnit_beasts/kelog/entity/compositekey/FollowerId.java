package com.hunnit_beasts.kelog.entity.compositekey;

import com.hunnit_beasts.kelog.entity.domain.User;
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
public class FollowerId implements Serializable {

    @Column(nullable = false)
    private Long following;

    @Column(nullable = false)
    private Long followed;

    public FollowerId(User following, User followed){
        this.following = following.getId();
        this.followed = followed.getId();
    }

}
