package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentReadResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentReplyListReadResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;
import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
                        new CaseBuilder()
                                .when(comment.parentReComment.isNotNull())
                                .then(comment.parentReComment.id)
                                .otherwise((Long) null)
                                .as("parentCommentId"),
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
    public List<CommentReadResponseDTO> findCommentReadResponseDTOsByPostId(Long postId) {
        QComment comment = QComment.comment;

        return jpaQueryFactory
                .select(Projections.constructor(CommentReadResponseDTO.class,
                        comment.id,
                        comment.user.thumbImage,
                        comment.user.nickname,
                        comment.regDate,
                        comment.commentContent.content,
                        comment.childReComments.size().castToNum(Long.class)
                        ))
                .from(comment)
                .where(comment.post.id.eq(postId).and(comment.parentReComment.isNull()))
                .orderBy(comment.regDate.asc())
                .fetch();
    }

    @Override
    public CommentReadResponseDTO findCommentReadResponseDTOByCommentId(Long commentId) {
        QComment comment = QComment.comment;
        QComment childComment = QComment.comment;

        return jpaQueryFactory
                .select(Projections.constructor(CommentReadResponseDTO.class,
                        comment.id,
                        comment.user.thumbImage,
                        comment.user.nickname,
                        comment.regDate,
                        comment.commentContent.content,
                        comment.childReComments.size().castToNum(Long.class)
                ))
                .from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();
    }

    @Override
    public CommentReplyListReadResponseDTO findCommentReplyListReadResponseDTOByCommentId(Long commentId) {
        QComment comment = QComment.comment;
        QComment childComment = QComment.comment;

        List<CommentReadResponseDTO> infos =  jpaQueryFactory
                .select(Projections.constructor(CommentReadResponseDTO.class,
                        comment.id,
                        comment.user.thumbImage,
                        comment.user.nickname,
                        comment.regDate,
                        comment.commentContent.content,
                        comment.childReComments.size().castToNum(Long.class)
                ))
                .from(comment)
                .where(comment.parentReComment.id.eq(commentId))
                .fetch();

        return new CommentReplyListReadResponseDTO(infos);
    }
}
