package com.hunnit_beasts.kelog.post.entity.compositekey;

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
public class TagPostId implements Serializable {

    @Column(length = 32,nullable = false)
    private String tagName;

    @Column(nullable = false)
    private Long postId;
}
