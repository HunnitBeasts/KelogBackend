package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;
import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
