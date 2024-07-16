package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.convert.UserSeriesInfos;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.QSeries;
import com.hunnit_beasts.kelog.postassist.entity.domain.QSeriesPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public UserSeriesResponseDTO findUserSeriesResponseDTOByUserId(Long userId) {
        QSeries series = QSeries.series1;
        List<UserSeriesInfos> userSeriesInfos = jpaQueryFactory
                .select(Projections.constructor(UserSeriesInfos.class,
                        series.id,
                        series.url,
                        series.seriesName))
                .from(series)
                .where(series.user.id.eq(userId))
                .fetch();
        return new UserSeriesResponseDTO(userSeriesInfos);
    }
}
