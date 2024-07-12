package com.hunnit_beasts.kelog.postassist.entity.compositekey;

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
public class SeriesPostId implements Serializable {

    @Column(nullable = false)
    private Long seriesId;

    @Column(nullable = false)
    private Long postId;
}
