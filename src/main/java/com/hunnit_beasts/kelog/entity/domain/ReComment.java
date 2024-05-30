package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.ReCommentId;
import com.hunnit_beasts.kelog.entity.superclass.RegEntity;
import jakarta.persistence.*;

@Entity
public class ReComment extends RegEntity {

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
