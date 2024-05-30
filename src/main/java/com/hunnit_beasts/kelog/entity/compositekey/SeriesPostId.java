package com.hunnit_beasts.kelog.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeExclude;

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
