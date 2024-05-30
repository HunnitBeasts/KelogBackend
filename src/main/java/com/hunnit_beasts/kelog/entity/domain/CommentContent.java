package com.hunnit_beasts.kelog.entity.domain;

import jakarta.persistence.*;

@Entity
public class CommentContent {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @OneToOne
    @MapsId
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
