package com.hunnit_beasts.kelog.compositekey;

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
public class IncompletePostId implements Serializable {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;
}
