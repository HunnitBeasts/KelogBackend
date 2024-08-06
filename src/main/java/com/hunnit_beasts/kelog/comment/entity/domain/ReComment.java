package com.hunnit_beasts.kelog.comment.entity.domain;

import com.hunnit_beasts.kelog.common.entity.superclass.RegEntity;
import jakarta.persistence.*;

@Entity
public class ReComment extends RegEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToOne
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;
}
