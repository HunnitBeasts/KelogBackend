package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.ReCommentId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ReComment {

    @EmbeddedId
    private ReCommentId id;

    @Column(nullable = false)
    private LocalDateTime regDate; //regDate 하나만 사용되므로 baseEntity 사용하지 않음

    @MapsId("parentCommentId")
    @OneToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @MapsId("childCommentId")
    @ManyToOne
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;
}
