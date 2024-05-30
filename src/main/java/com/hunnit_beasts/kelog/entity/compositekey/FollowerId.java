package com.hunnit_beasts.kelog.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FollowerId implements Serializable {

    @Column(nullable = false)
    private Long following;

    @Column(nullable = false)
    private Long followed;
}
