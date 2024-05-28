package com.hunnit_beasts.kelog.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CommentContent commentContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Recomment
    @OneToOne(mappedBy = "parentComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ReComment parentComment;

    @OneToMany(mappedBy = "childComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReComment> childComments = new ArrayList<>();
}
