package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.convert.SeriesPostInfo;
import com.hunnit_beasts.kelog.postassist.dto.convert.UserSeriesInfo;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesReadResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.QSeries;
import com.hunnit_beasts.kelog.postassist.entity.domain.QSeriesPost;
import com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<SeriesPostInfo> seriesPostInfos = jpaQueryFactory
                .select(Projections.constructor(SeriesPostInfo.class,
                        seriesPost.post.id,
                        seriesPost.post.user.userId,
                        seriesPost.post.url,
                        seriesPost.post.thumbImage,
                        seriesPost.post.title,
                        seriesPost.post.regDate,
                        seriesPost.post.shortContent))
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
        List<UserSeriesInfo> userSeriesInfos = jpaQueryFactory
                .select(Projections.constructor(UserSeriesInfo.class,
                        series.id,
                        series.url,
                        series.seriesName))
                .from(series)
                .where(series.user.id.eq(userId))
                .fetch();
        return new UserSeriesResponseDTO(userSeriesInfos);
    }

    @Override
    public List<SeriesPost> updateOrder(Long seriesId, List<Long> posts) {
        QSeriesPost seriesPost = QSeriesPost.seriesPost;
        List<SeriesPost> seriesPosts = jpaQueryFactory
                .selectFrom(seriesPost)
                .where(seriesPost.id.seriesId.eq(seriesId))
                .fetch();

        Map<Long, SeriesPost> seriesPostMap = seriesPosts.stream()
                .collect(Collectors.toMap(sp -> sp.getPost().getId(), sp -> sp));

        for (int i = 0; i < posts.size(); i++) {
            Long postId = posts.get(i);
            SeriesPost sp = seriesPostMap.get(postId);
            if (sp != null)
                sp.setSeriesOrder((long) i + 1);
        }

        return seriesPosts.stream()
                .sorted(Comparator.comparing(SeriesPost::getSeriesOrder))
                .collect(Collectors.toList());
    }

}
