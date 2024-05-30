package com.hunnit_beasts.kelog.entity.domain;

import jakarta.persistence.*;

@Entity
public class PostContent {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id")
    private Post post;
}
