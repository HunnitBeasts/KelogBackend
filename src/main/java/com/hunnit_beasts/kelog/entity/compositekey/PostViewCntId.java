package com.hunnit_beasts.kelog.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostViewCntId implements Serializable {

    @Column(nullable = false)
    private Long commentId;

    @Column(nullable = false)
    private LocalDateTime regDate;
}
