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
@Table(name = "comment", indexes = {
        @Index(name = "idx_reg_date", columnList = "reg_date")
})
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
    @OneToMany(mappedBy = "parentReComment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Comment> childReComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentReComment;

    public Comment(CommentCreateRequestDTO dto, Post post, User user){
        this.commentContent = new CommentContent(dto.getContent(), this);
        this.user = user;
        this.post = post;
    }

    public Comment(CommentCreateRequestDTO dto, Post post, User user, Comment parentComment){
        this.commentContent = new CommentContent(dto.getContent(), this);
        this.user = user;
        this.post = post;
        if (parentComment != null)
            parentComment.addChildComment(this);
    }

    public void addChildComment(Comment childComment) {
        childComment.parentReComment = this;
        this.childReComments.add(childComment);
    }
}
