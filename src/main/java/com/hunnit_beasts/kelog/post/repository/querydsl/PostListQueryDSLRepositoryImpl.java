package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.hunnit_beasts.kelog.post.dto.convert.PostPageConvert;
import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserLikePostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.dto.superclass.PageRequestDTO;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.entity.domain.QLikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPostViewCnt;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.enumeration.TrendType;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostListQueryDSLRepositoryImpl implements PostListQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PostPageResponseDTO findByPostPageDTOs(PostPageRequestDTO dto) {
        return findPosts(dto, this::createWhereConditions, this::getOrderSpecifier);
    }

    @Override
    public PostPageResponseDTO findByLikePostDTOs(UserLikePostRequestDTO dto) {
        return findPosts(dto, this::createUserLikeWhereConditions, this::getOrderSpecifier);
    }

    @Override
    public PostPageResponseDTO findByTrendPostDTOs(TrendPostRequestDTO dto) {
        return findPosts(dto, this::createTrendingWhereConditions, this::getTrendingOrderSpecifier);
    }

    private <T extends PageRequestDTO> PostPageResponseDTO findPosts(T dto, Function<T, BooleanBuilder> whereConditionsBuilder, Function<T, OrderSpecifier<?>> orderSpecifierFunction) {
        BooleanBuilder whereConditions = whereConditionsBuilder.apply(dto);

        List<Post> posts = fetchPosts(whereConditions, dto, orderSpecifierFunction.apply(dto));
        List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());

        Map<Long, Long> commentCounts = getEntityCounts(postIds, QComment.comment, QComment.comment.post.id, dto);
        Map<Long, Long> likeCounts = getEntityCounts(postIds, QLikedPost.likedPost, QLikedPost.likedPost.post.id, dto);

        List<PostPageConvert> postConverts = posts.stream()
                .map(p -> mapToPostPageConvert(p, commentCounts.get(p.getId()), likeCounts.get(p.getId())))
                .collect(Collectors.toList());

        long totalCount = getTotalCount(whereConditions);

        return new PostPageResponseDTO(totalCount, postConverts);
    }

    private <T extends PageRequestDTO> List<Post> fetchPosts(BooleanBuilder whereConditions, T dto, OrderSpecifier<?> orderSpecifier) {
        return jpaQueryFactory
                .selectFrom(QPost.post)
                .where(whereConditions)
                .orderBy(orderSpecifier)
                .offset((dto.getPage() - 1) * dto.getSize())
                .limit(dto.getSize())
                .fetch();
    }

    // TODO : 현재 사이트 규모가 작아서 1차적으로 모든 날자에 포함된 데이터를 갖고오지만 나중에 규모가 커진다면
    //  몇백 몇천개의 데이터를 가져올수 있습니다. 따라서 나중에 이 조건을 좀더 까다롭게 (ex. 댓글 수 일정이상, 좋아요 수 일정 이상 등)
    //  하여 가져오는 것이 서버 부하가 덜 할것으로 예상됩니다.
    private BooleanBuilder createTrendingWhereConditions(TrendPostRequestDTO dto) {
        QPost post = QPost.post;
        LocalDateTime startDate = getStartDateForPeriod(dto.getType());

        return new PostQueryBuilder()
                .addPublicCondition(true)
                .addSearchCondition(dto.getSearch())
                .addDateCondition(post.regDate.goe(startDate))
                .build();
    }

    private BooleanBuilder createWhereConditions(PostPageRequestDTO dto) {
        return new PostQueryBuilder()
                .addPublicCondition(true)
                .addTypeCondition(PostType.NORMAL)
                .addTagCondition(dto.getTagName())
                .addSearchCondition(dto.getSearch())
                .addUserIdCondition(dto.getUserId())
                .build();
    }

    private BooleanBuilder createUserLikeWhereConditions(UserLikePostRequestDTO dto) {
        return new PostQueryBuilder()
                .addPublicCondition(true)
                .addLikedPostCondition(dto.getUserId())
                .addSearchCondition(dto.getSearch())
                .build();
    }

    private OrderSpecifier<?> getOrderSpecifier(PageRequestDTO dto) {
        QPost post = QPost.post;
        return dto.getSort().equals("title") ? post.title.desc() : post.regDate.desc();
    }

    private OrderSpecifier<?> getTrendingOrderSpecifier(PageRequestDTO dto) {
        if (dto instanceof TrendPostRequestDTO trendDto)
            return calculateTrendScore(trendDto).desc();
        return QPost.post.regDate.desc();
    }

    private <T extends EntityPathBase<?>> Map<Long, Long> getEntityCounts(List<Long> postIds, T entity, NumberPath<Long> postIdPath, PageRequestDTO dto) {
        LocalDateTime startDate = (dto instanceof TrendPostRequestDTO)
                ? getStartDateForPeriod(((TrendPostRequestDTO) dto).getType())
                : LocalDateTime.now().minusYears(100);

        return jpaQueryFactory
                .select(postIdPath, entity.count())
                .from(entity)
                .where(postIdPath.in(postIds).and(getDatePath(entity).goe(startDate)))
                .groupBy(postIdPath)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> Optional.ofNullable(tuple.get(1, Long.class)).orElse(0L)
                ));
    }

    private DateTimePath<LocalDateTime> getDatePath(EntityPathBase<?> entity) {
        if (entity.equals(QComment.comment)) {
            return QComment.comment.regDate;
        } else if (entity.equals(QLikedPost.likedPost)) {
            return QLikedPost.likedPost.regDate;
        }
        return QPost.post.regDate;
    }

    private long getTotalCount(BooleanBuilder whereConditions) {
        QPost post = QPost.post;
        return Optional.ofNullable(jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(whereConditions)
                .fetchOne()).orElse(0L);
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

    private NumberExpression<Double> calculateTrendScore(TrendPostRequestDTO dto) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;
        QLikedPost likedPost = QLikedPost.likedPost;
        QPostViewCnt postViewCnt = QPostViewCnt.postViewCnt;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = getStartDateForPeriod(dto.getType());

        NumberExpression<Double> baseScore = Expressions.numberTemplate(Double.class,
                "COALESCE({0}, 0) * 2.0 + COALESCE({1}, 0) * 1.5 + COALESCE({2}, 0) * 1.0",
                JPAExpressions.select(likedPost.count())
                        .from(likedPost)
                        .where(likedPost.post.eq(post).and(likedPost.regDate.goe(startDate))),
                JPAExpressions.select(comment.count())
                        .from(comment)
                        .where(comment.post.eq(post).and(comment.regDate.goe(startDate))),
                JPAExpressions.select(postViewCnt.viewCnt.sum())
                        .from(postViewCnt)
                        .where(postViewCnt.id.postId.eq(post.id).and(postViewCnt.id.regDate.goe(startDate)))
        );

        NumberExpression<Double> recencyWeight;

        if (dto.getType() == TrendType.DAY) {
            recencyWeight = new CaseBuilder()
                    .when(post.regDate.goe(now.minusHours(6))).then(Expressions.numberTemplate(Double.class, "4.0"))
                    .when(post.regDate.goe(now.minusHours(12))).then(Expressions.numberTemplate(Double.class, "3.0"))
                    .when(post.regDate.goe(now.minusHours(18))).then(Expressions.numberTemplate(Double.class, "2.0"))
                    .otherwise(Expressions.numberTemplate(Double.class, "1.0"));
        } else if (dto.getType() == TrendType.WEEK) {
            recencyWeight = new CaseBuilder()
                    .when(post.regDate.goe(now.minusDays(1))).then(Expressions.numberTemplate(Double.class, "7.0"))
                    .when(post.regDate.goe(now.minusDays(3))).then(Expressions.numberTemplate(Double.class, "5.0"))
                    .when(post.regDate.goe(now.minusDays(5))).then(Expressions.numberTemplate(Double.class, "3.0"))
                    .otherwise(Expressions.numberTemplate(Double.class, "1.0"));
        } else if (dto.getType() == TrendType.MONTH) {
            recencyWeight = new CaseBuilder()
                    .when(post.regDate.goe(now.minusWeeks(1))).then(Expressions.numberTemplate(Double.class, "4.0"))
                    .when(post.regDate.goe(now.minusWeeks(2))).then(Expressions.numberTemplate(Double.class, "3.0"))
                    .when(post.regDate.goe(now.minusWeeks(3))).then(Expressions.numberTemplate(Double.class, "2.0"))
                    .otherwise(Expressions.numberTemplate(Double.class, "1.0"));
        } else if (dto.getType() == TrendType.YEAR) {
            recencyWeight = new CaseBuilder()
                    .when(post.regDate.goe(now.minusMonths(1))).then(Expressions.numberTemplate(Double.class, "12.0"))
                    .when(post.regDate.goe(now.minusMonths(3))).then(Expressions.numberTemplate(Double.class, "9.0"))
                    .when(post.regDate.goe(now.minusMonths(6))).then(Expressions.numberTemplate(Double.class, "6.0"))
                    .when(post.regDate.goe(now.minusMonths(9))).then(Expressions.numberTemplate(Double.class, "3.0"))
                    .otherwise(Expressions.numberTemplate(Double.class, "1.0"));
        } else {
            recencyWeight = Expressions.numberTemplate(Double.class, "1.0");
        }

        return baseScore.multiply(recencyWeight);
    }

    private LocalDateTime getStartDateForPeriod(TrendType period) {
        LocalDateTime now = LocalDateTime.now();
        return switch (period) {
            case DAY -> now.minusDays(1);
            case WEEK -> now.minusWeeks(1);
            case MONTH -> now.minusMonths(1);
            case YEAR -> now.minusYears(1);
        };
    }

    private static class PostQueryBuilder {
        private final BooleanBuilder builder = new BooleanBuilder();
        private final QPost post = QPost.post;

        PostQueryBuilder addPublicCondition(Boolean boll) {
            builder.and(post.isPublic.eq(boll));
            return this;
        }

        PostQueryBuilder addTypeCondition(PostType type) {
            builder.and(post.type.eq(type));
            return this;
        }

        PostQueryBuilder addTagCondition(String tagName) {
            Optional.ofNullable(tagName).ifPresent(tag -> {
                QTagPost tagPost = QTagPost.tagPost;
                builder.and(post.id.in(
                        JPAExpressions.select(tagPost.post.id)
                                .from(tagPost)
                                .where(tagPost.tag.tagName.eq(tag))
                ));
            });
            return this;
        }

        PostQueryBuilder addSearchCondition(String search) {
            Optional.ofNullable(search).ifPresent(s -> builder.and(post.title.contains(s)));
            return this;
        }

        PostQueryBuilder addUserIdCondition(Long userId) {
            Optional.ofNullable(userId).ifPresent(id -> builder.and(post.user.id.eq(id)));
            return this;
        }

        PostQueryBuilder addLikedPostCondition(Long userId) {
            QLikedPost likedPost = QLikedPost.likedPost;
            Optional.ofNullable(userId).ifPresent(id ->
                    builder.and(post.id.in(
                            JPAExpressions.select(likedPost.post.id)
                                    .from(likedPost)
                                    .where(likedPost.user.id.eq(id))
                    ))
            );
            return this;
        }

        PostQueryBuilder addDateCondition(BooleanExpression dateExpression) {
            builder.and(dateExpression);
            return this;
        }

        BooleanBuilder build() {
            return builder;
        }
    }
}