package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.hunnit_beasts.kelog.post.dto.convert.PostInfos;
import com.hunnit_beasts.kelog.post.dto.convert.PostPageConvert;
import com.hunnit_beasts.kelog.post.dto.info.PostOrderInfo;
import com.hunnit_beasts.kelog.post.dto.info.ViewCntInfo;
import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostUpdateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostViewCountResponseDTO;
import com.hunnit_beasts.kelog.post.entity.domain.QLikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPostContent;
import com.hunnit_beasts.kelog.post.entity.domain.QPostViewCnt;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Log4j2
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PostCreateResponseDTO findPostCreateResponseDTOById(Long id) {
        QPost post = QPost.post;
        QTagPost tagPost = QTagPost.tagPost;
        PostInfos postInfos = jpaQueryFactory
                .select(Projections.constructor(PostInfos.class,
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
        List<String> tags = jpaQueryFactory
                .select(tagPost.tag.tagName)
                .from(tagPost)
                .where(tagPost.id.postId.eq(id))
                .fetch();
        return new PostCreateResponseDTO(Objects.requireNonNull(postInfos),tags);
    }

    @Override
    public Long findTotalViewCntById(Long id) {
        return todayViewCnt(id);
    }

    @Override
    public PostUpdateResponseDTO findPostUpdateResponseDTOById(Long id) {
        QPost post = QPost.post;
        QTagPost tagPost = QTagPost.tagPost;

        PostInfos postInfos = jpaQueryFactory
                .select(Projections.constructor(PostInfos.class,
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
        List<String> tags = jpaQueryFactory
                .select(tagPost.tag.tagName)
                .from(tagPost)
                .where(tagPost.id.postId.eq(id))
                .fetch();
        return new PostUpdateResponseDTO(Objects.requireNonNull(postInfos),tags);
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

    @Override
    public boolean existsByUserIdAndPostId(Long userId, Long postId) {
        if (userId == null) return false;

        QLikedPost likedPost = QLikedPost.likedPost;
        return jpaQueryFactory
                .selectOne()
                .from(likedPost)
                .where(likedPost.user.id.eq(userId)
                        .and(likedPost.post.id.eq(postId)))
                .fetchFirst() != null;
    }

    @Override
    public Long countByPostId(Long postId) {
        QLikedPost likedPost = QLikedPost.likedPost;
        return jpaQueryFactory
                .select(likedPost.count())
                .from(likedPost)
                .where(likedPost.post.id.eq(postId))
                .fetchOne();
    }

    @Override
    public List<String> findTagsByPostId(Long postId) {
        QTagPost tagPost = QTagPost.tagPost;
        return jpaQueryFactory
                .select(tagPost.tag.tagName)
                .from(tagPost)
                .where(tagPost.post.id.eq(postId))
                .fetch();
    }

    @Override
    public String findContentByPostId(Long postId) {
        QPostContent postContent = QPostContent.postContent;
        return jpaQueryFactory
                .select(postContent.content)
                .from(postContent)
                .where(postContent.post.id.eq(postId))
                .fetchOne();
    }

    @Override
    public PostOrderInfo findNextPostByUser(Long userId, Long postId) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(Projections.constructor(PostOrderInfo.class,
                        post.title,
                        post.id,
                        post.url))
                .from(post)
                .where(post.user.id.eq(userId)
                        .and(post.id.gt(postId)))
                .orderBy(post.id.asc())
                .fetchFirst();
    }

    @Override
    public PostOrderInfo findPreviousPostByUser(Long userId, Long postId) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(Projections.constructor(PostOrderInfo.class,
                        post.title,
                        post.id,
                        post.url))
                .from(post)
                .where(post.user.id.eq(userId)
                        .and(post.id.lt(postId)))
                .orderBy(post.id.desc())
                .fetchFirst();
    }

    @Override
    public Long findPostIdByUserIdAndPostUrl(String userId, String url) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(post.id)
                .from(post)
                .where(post.user.userId.eq(userId).and(post.url.eq(url)))
                .fetchOne();
    }

    @Override
    public PostPageResponseDTO findByPostPageDTO(PostPageRequestDTO dto) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;
        QLikedPost likedPost = QLikedPost.likedPost;
        QTagPost tagPost = QTagPost.tagPost;

        JPAQuery<Tuple> query = jpaQueryFactory
                .select(post.id,
                        post.thumbImage,
                        post.title,
                        post.shortContent,
                        post.regDate,
                        post.user.thumbImage,
                        post.user.nickname,
                        comment.id.countDistinct().as("commentCount"),
                        likedPost.likedPostId.userId.countDistinct().as("likeCount"))
                .from(post)
                .leftJoin(comment).on(comment.post.eq(post))
                .leftJoin(likedPost).on(likedPost.post.eq(post))
                .leftJoin(tagPost).on(tagPost.post.eq(post))
                .where(createWhereConditions(dto))
                .groupBy(post.id, post.thumbImage, post.title, post.shortContent, post.regDate, post.user.thumbImage, post.user.nickname);

        applySort(query, dto.getSort());

        Long totalCountResult = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(createWhereConditions(dto))
                .fetchOne();

        long totalCount = totalCountResult != null ? totalCountResult : 0L;

        List<Tuple> results = query
                .offset((dto.getPage() - 1) * dto.getSize())
                .limit(dto.getSize())
                .fetch();

        List<PostPageConvert> posts = results.stream()
                .map(tuple -> new PostPageConvert(
                        tuple.get(post.id),
                        tuple.get(post.thumbImage),
                        tuple.get(post.title),
                        tuple.get(post.shortContent),
                        tuple.get(post.regDate),
                        tuple.get(7, Long.class) != null ? tuple.get(7, Long.class) : 0L,
                        tuple.get(post.user.thumbImage),
                        tuple.get(post.user.nickname),
                        tuple.get(8, Long.class) != null ? tuple.get(8, Long.class) : 0L
                ))
                .collect(Collectors.toList());

        return new PostPageResponseDTO(totalCount, posts);
    }

    private BooleanExpression[] createWhereConditions(PostPageRequestDTO dto) {
        List<BooleanExpression> conditions = new ArrayList<>();
        QPost post = QPost.post;
        QTagPost tagPost = QTagPost.tagPost;

        if (dto.getTagName() != null && !dto.getTagName().isEmpty())
            conditions.add(tagPost.tag.tagName.eq(dto.getTagName()));

        if (dto.getSearch() != null && !dto.getSearch().isEmpty())
            conditions.add(post.title.contains(dto.getSearch()));

        if (dto.getUserId() != null)
            conditions.add(post.user.id.eq(dto.getUserId()));

        return conditions.toArray(new BooleanExpression[0]);
    }

    private void applySort(JPAQuery<Tuple> query, String sort) {
        QPost post = QPost.post;
        QLikedPost likedPost = QLikedPost.likedPost;

        if ("likes".equals(sort))
            query.orderBy(likedPost.likedPostId.userId.countDistinct().desc(), post.regDate.desc());
        else
            query.orderBy(post.regDate.desc());

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
