package com.hunnit_beasts.kelog.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostViewCntId implements Serializable {

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private LocalDateTime regDate;

    public PostViewCntId(Long postId){
        this.postId = postId;
        this.regDate = LocalDate.now().atStartOfDay();
    }
}
