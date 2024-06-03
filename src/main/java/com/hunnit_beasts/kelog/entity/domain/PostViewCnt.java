package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.PostViewCntId;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class PostViewCnt {

    @Id
    private PostViewCntId id;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCnt;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
