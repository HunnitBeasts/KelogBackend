package com.hunnit_beasts.kelog.post.entity.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostContent {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id")
    private Post post;

    public PostContent(String content, Post post) {
        this.content = content;
        this.post = post;
    }
}
