package com.hunnit_beasts.kelog.user.entity.compositekey;

import com.hunnit_beasts.kelog.user.enumeration.SocialType;
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
public class SocialInfoId implements Serializable {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private SocialType socialType;

}
