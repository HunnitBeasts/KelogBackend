package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.entity.domain.QSeriesPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeriesQueryDSLRepositoryImpl implements SeriesQueryDSLRepository{

    private final JPAQueryFactory jpaQueryFactory;

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
