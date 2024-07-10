package com.hunnit_beasts.kelog.comment.entity.compositekey;

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
public class ReCommentId implements Serializable {

    @Column(nullable = false)
    private Long parentCommentId;

    @Column(nullable = false)
    private Long childCommentId;
}
