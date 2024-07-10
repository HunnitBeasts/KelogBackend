package com.hunnit_beasts.kelog.repository.querydslimpl;

import com.hunnit_beasts.kelog.dto.response.post.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostUpdateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.QPost;
import com.hunnit_beasts.kelog.entity.domain.QPostViewCnt;
import com.hunnit_beasts.kelog.entity.domain.QSeriesPost;
import com.hunnit_beasts.kelog.repository.querydsl.PostQueryDSLRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PostCreateResponseDTO findPostCreateResponseDTOById(Long id) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(Projections.constructor(PostCreateResponseDTO.class,
                        post.id,
                        post.user.id,
                        post.title,
                        post.type,
                        post.thumbImage,
                        post.isPublic,
                        post.shortContent,
                        post.url,
                        post.postContent.content,
                        post.regDate,
                        post.modDate))
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public Long findTotalViewCntById(Long id) {
        QPostViewCnt postViewCnt = QPostViewCnt.postViewCnt;
        return jpaQueryFactory
                .select(postViewCnt.viewCnt.sum())
                .from(postViewCnt)
                .where(postViewCnt.id.postId.eq(id))
                .fetchOne();
    }

    @Override
    public PostUpdateResponseDTO findPostUpdateResponseDTOById(Long id) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(Projections.constructor(PostUpdateResponseDTO.class,
                        post.id,
                        post.title,
                        post.type,
                        post.thumbImage,
                        post.isPublic,
                        post.shortContent,
                        post.url,
                        post.postContent.content))
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public Long findMaxOrderBySeriesId(Long seriesId) {
        QSeriesPost seriesPost = QSeriesPost.seriesPost;
        Long maxOrder = jpaQueryFactory
                .select(seriesPost.seriesOrder.max())
                .from(seriesPost)
                .where(seriesPost.id.seriesId.eq(seriesId))
                .fetchOne();
        return maxOrder != null ? maxOrder : 0L;
    }
}
