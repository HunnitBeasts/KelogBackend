package com.hunnit_beasts.kelog.comment.entity.domain;


import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.common.entity.superclass.BaseEntity;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.user.entity.domain.User;
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
    @OneToMany(mappedBy = "parentComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<ReComment> childReComments = new ArrayList<>();;

    @OneToOne(mappedBy = "childComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ReComment parentReComment;

    public Comment(CommentCreateRequestDTO dto, Post post, User user){
        this.commentContent = new CommentContent(dto.getContent(), this);
        this.user = user;
        this.post = post;
    }
}
