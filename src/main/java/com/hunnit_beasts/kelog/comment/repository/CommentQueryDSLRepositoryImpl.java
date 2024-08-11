package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.dto.convert.CommentListInfo;
import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;
import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.hunnit_beasts.kelog.comment.entity.domain.QCommentContent;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.user.entity.domain.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryDSLRepositoryImpl implements CommentQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CommentCreateResponseDTO findCommentCreateResponseDTOById(Long id) {
        QComment comment = QComment.comment;
        return jpaQueryFactory
                .select(Projections.constructor(CommentCreateResponseDTO.class,
                        comment.id,
                        comment.user.id,
                        comment.post.id,
                        comment.commentContent.content,
                        comment.regDate,
                        comment.modDate))
                .from(comment)
                .where(comment.id.eq(id))
                .fetchOne();
    }

    @Override
    public CommentUpdateResponseDTO findCommentUpdateResponseDTOById(Long id) {
        QComment comment = QComment.comment;
        return jpaQueryFactory
                .select(Projections.constructor(CommentUpdateResponseDTO.class,
                        comment.id,
                        comment.commentContent.content,
                        comment.modDate))
                .from(comment)
                .where(comment.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<CommentListInfo> findCommentListInfosByPostId(Long postId) {
        QComment comment = QComment.comment;
        QCommentContent commentContent = QCommentContent.commentContent;
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<CommentListInfo> results = jpaQueryFactory
                .select(Projections.fields(CommentListInfo.class,
                        comment.id,
                        user.thumbImage,
                        user.nickname,
                        comment.regDate,
                        commentContent.content,
                        comment.childReComments.size().as("replyCount")
                        ))
                .from(comment)
                .join(comment.commentContent,commentContent)
                .join(comment.user,user)
                .where(comment.post.id.eq(post.id))
                .fetch();

        for(CommentListInfo commentListInfo : results) {
            Long level = calculateLevel(commentListInfo.getId());
            commentListInfo.setLevel(level);
        }

        return results;
    }

    private Long calculateLevel(Long commentId){
        QComment comment = QComment.comment;
        Long level = 0L;

        Long parentId = jpaQueryFactory
                .select(comment.parentReComment.id)
                .from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();

        while(parentId != null){
            level++;
            parentId = jpaQueryFactory
                    .select(comment.parentReComment.id)
                    .from(comment)
                    .where(comment.id.eq(commentId))
                    .fetchOne();
        }

        return level;
    }
}
