package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.convert.UserSeriesInfos;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.QSeries;
import com.hunnit_beasts.kelog.postassist.dto.convert.SeriesPostInfos;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesReadResponseDTO;
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
    public SeriesReadResponseDTO findSeriesReadResponseDTOById(Long seriesId) {
        QSeriesPost seriesPost = QSeriesPost.seriesPost;
        QSeries series = QSeries.series1;
        List<SeriesPostInfos> seriesPostInfos = jpaQueryFactory
                .select(Projections.constructor(SeriesPostInfos.class,
                        seriesPost.post.id,
                        seriesPost.post.user.userId,
                        seriesPost.post.url,
                        seriesPost.post.title))
                .from(seriesPost)
                .where(seriesPost.id.seriesId.eq(seriesId))
                .orderBy(seriesPost.seriesOrder.asc())
                .fetch();
        String seriesName = jpaQueryFactory
                .select(series.seriesName)
                .from(series)
                .where(series.id.eq(seriesId))
                .fetchOne();
        return new SeriesReadResponseDTO(seriesName,seriesPostInfos);
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
