package com.hunnit_beasts.kelog.comment.entity.domain;

import com.hunnit_beasts.kelog.comment.dto.request.CommentUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentContent {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @OneToOne
    @MapsId
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentContent(String content, Comment comment){
        this.content = content;
        this.comment = comment;
    }

    public CommentContent commentContentUpdate(CommentUpdateRequestDTO dto){
        this.content = dto.getContent();
        return this;
    }
}
