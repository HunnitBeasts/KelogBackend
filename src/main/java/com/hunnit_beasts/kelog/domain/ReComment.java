package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.ReCommentId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ReComment {

    @EmbeddedId
    private ReCommentId id;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @MapsId("parentCommentId")
    @OneToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @MapsId("childCommentId")
    @ManyToOne
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;
}
