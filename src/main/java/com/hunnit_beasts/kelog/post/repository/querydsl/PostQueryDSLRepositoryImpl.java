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
import com.hunnit_beasts.kelog.post.entity.domain.*;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
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
    public Long getPostIdByUserIdAndPostUrl(String userId, String url) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(post.id)
                .from(post)
                .where(post.user.userId.eq(userId).and(post.url.eq(url)))
                .fetchOne();
    }

    @Override
    public Long getUserCountByUserId(Long userId) {
        QPost post = QPost.post;
        return jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(post.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public PostPageResponseDTO findByPostPageDTO(PostPageRequestDTO dto) {
        QPost post = QPost.post;

        BooleanBuilder whereConditions = createWhereConditions(dto);

        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .where(whereConditions)
                .orderBy(getOrderSpecifier(dto.getSort()))
                .offset((dto.getPage() - 1) * dto.getSize())
                .limit(dto.getSize())
                .fetch();

        List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());

        Map<Long, Long> commentCounts = getCommentCounts(postIds);
        Map<Long, Long> likeCounts = getLikeCounts(postIds);

        List<PostPageConvert> postConverts = posts.stream()
                .map(p -> mapToPostPageConvert(p, commentCounts.get(p.getId()), likeCounts.get(p.getId())))
                .collect(Collectors.toList());

        long totalCount = Optional.ofNullable(jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(whereConditions)
                .fetchOne()).orElse(0L);

        return new PostPageResponseDTO(totalCount, postConverts);
    }

    private BooleanBuilder createWhereConditions(PostPageRequestDTO dto) {
        QPost post = QPost.post;
        QTagPost tagPost = QTagPost.tagPost;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(post.isPublic.eq(Boolean.TRUE))
                .and(post.type.eq(PostType.NORMAL));

        Optional.ofNullable(dto.getTagName()).ifPresent(tag ->
                builder.and(post.id.in(
                        JPAExpressions.select(tagPost.post.id)
                                .from(tagPost)
                                .where(tagPost.tag.tagName.eq(tag))
                ))
        );
        Optional.ofNullable(dto.getSearch()).ifPresent(search ->
                builder.and(post.title.contains(search)));
        Optional.ofNullable(dto.getUserId()).ifPresent(userId ->
                builder.and(post.user.id.eq(userId)));

        return builder;
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort) {
        QPost post = QPost.post;
        // TODO : 추후 sort 요건에 따라 추가 하겠습니다.
        if (sort.equals("title"))
            return post.title.desc();
        else
            return post.regDate.desc();
    }

    private Map<Long, Long> getCommentCounts(List<Long> postIds) {
        QComment comment = QComment.comment;
        return jpaQueryFactory
                .select(comment.post.id, comment.count())
                .from(comment)
                .where(comment.post.id.in(postIds))
                .groupBy(comment.post.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> Optional.ofNullable(tuple.get(1, Long.class)).orElse(0L)
                ));
    }

    private Map<Long, Long> getLikeCounts(List<Long> postIds) {
        QLikedPost likedPost = QLikedPost.likedPost;
        return jpaQueryFactory
                .select(likedPost.post.id, likedPost.count())
                .from(likedPost)
                .where(likedPost.post.id.in(postIds))
                .groupBy(likedPost.post.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> Optional.ofNullable(tuple.get(1, Long.class)).orElse(0L)
                ));
    }

    private PostPageConvert mapToPostPageConvert(Post post, Long commentCount, Long likeCount) {
        return new PostPageConvert(
                post.getId(),
                post.getThumbImage(),
                post.getTitle(),
                post.getShortContent(),
                post.getRegDate(),
                commentCount != null ? commentCount : 0L,
                post.getUser().getThumbImage(),
                post.getUser().getNickname(),
                likeCount != null ? likeCount : 0L
        );
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
