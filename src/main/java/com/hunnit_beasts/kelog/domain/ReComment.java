package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.ReCommentId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ReComment extends RegEntity{

    @EmbeddedId
    private ReCommentId id;

    @MapsId("parentCommentId")
    @OneToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @MapsId("childCommentId")
    @ManyToOne
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;
}
