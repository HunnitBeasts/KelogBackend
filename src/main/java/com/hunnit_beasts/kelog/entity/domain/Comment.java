package com.hunnit_beasts.kelog.entity.domain;


import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.entity.superclass.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CommentContent commentContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //Recomment
    @OneToOne(mappedBy = "parentComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ReComment parentComment;

    @OneToMany(mappedBy = "childComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<ReComment> childComments = new ArrayList<>();

    public Comment(CommentCreateRequestDTO dto, Post post, User user){
        this.commentContent = new CommentContent(dto.getContent(), this);
        this.user = user;
        this.post = post;
    }
}
