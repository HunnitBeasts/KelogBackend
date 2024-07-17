package com.hunnit_beasts.kelog.comment.entity.domain;

import com.hunnit_beasts.kelog.comment.entity.compositekey.ReCommentId;
import com.hunnit_beasts.kelog.common.entity.superclass.RegEntity;
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
    @ManyToOne // 1 TO 1 일듯
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;
}
