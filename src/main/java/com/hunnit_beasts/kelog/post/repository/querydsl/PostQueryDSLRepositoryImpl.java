package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.post.dto.info.ViewCntInfo;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostUpdateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostViewCountResponseDTO;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPostViewCnt;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        return todayViewCnt(id);
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
    public PostViewCountResponseDTO findViewCntInfosById(Long id) {
        QPostViewCnt postViewCnt = QPostViewCnt.postViewCnt;
        QPost post = QPost.post;

        LocalDateTime now = LocalDate.now().atStartOfDay();
        Long totalViewCnt = findTotalViewCntById(id);
        Long todayViewCnt = targetDayViewCnt(id, now);
        Long yesterdayViewCnt = targetDayViewCnt(id, now.minusDays(1));
        List<ViewCntInfo> views = jpaQueryFactory
                .select(Projections.constructor(ViewCntInfo.class,
                        postViewCnt.viewCnt,
                        postViewCnt.id.regDate))
                .from(postViewCnt)
                .where(postViewCnt.id.postId.eq(id))
                .fetch();
        LocalDateTime regDate = jpaQueryFactory
                .select(post.regDate)
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();

        return new PostViewCountResponseDTO(totalViewCnt,todayViewCnt,yesterdayViewCnt,views,regDate);
    }

    private Long todayViewCnt(Long postId){
        QPostViewCnt postViewCnt = QPostViewCnt.postViewCnt;
        return jpaQueryFactory
                .select(postViewCnt.viewCnt.sum())
                .from(postViewCnt)
                .where(postViewCnt.id.postId.eq(postId))
                .fetchOne();
    }

    private Long targetDayViewCnt(Long postId, LocalDateTime day){
        QPostViewCnt postViewCnt = QPostViewCnt.postViewCnt;
        return jpaQueryFactory
                .select(postViewCnt.viewCnt)
                .from(postViewCnt)
                .where(postViewCnt.id.postId.eq(postId).and(postViewCnt.id.regDate.eq(day)))
                .fetchOne();
    }

}
