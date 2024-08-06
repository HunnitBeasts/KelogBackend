package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.hunnit_beasts.kelog.post.dto.convert.PostPageConvert;
import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserLikePostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.dto.superclass.PageRequestDTO;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.entity.domain.QLikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
        return findPosts(dto, this::createWhereConditions);
    }

    @Override
    public PostPageResponseDTO findByLikePostDTOs(UserLikePostRequestDTO dto) {
        return findPosts(dto, this::createUserLikeWhereConditions);
    }

    private <T extends PageRequestDTO> PostPageResponseDTO findPosts(T dto, Function<T, BooleanBuilder> whereConditionsBuilder) {
        BooleanBuilder whereConditions = whereConditionsBuilder.apply(dto);

        List<Post> posts = fetchPosts(whereConditions, dto);
        List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());

        Map<Long, Long> commentCounts = getEntityCounts(postIds, QComment.comment, QComment.comment.post.id);
        Map<Long, Long> likeCounts = getEntityCounts(postIds, QLikedPost.likedPost, QLikedPost.likedPost.post.id);

        List<PostPageConvert> postConverts = posts.stream()
                .map(p -> mapToPostPageConvert(p, commentCounts.get(p.getId()), likeCounts.get(p.getId())))
                .collect(Collectors.toList());

        long totalCount = getTotalCount(whereConditions);

        return new PostPageResponseDTO(totalCount, postConverts);
    }

    private <T extends PageRequestDTO> List<Post> fetchPosts(BooleanBuilder whereConditions, T dto) {
        return jpaQueryFactory
                .selectFrom(QPost.post)
                .where(whereConditions)
                .orderBy(getOrderSpecifier(dto.getSort()))
                .offset((dto.getPage() - 1) * dto.getSize())
                .limit(dto.getSize())
                .fetch();
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

    private OrderSpecifier<?> getOrderSpecifier(String sort) {
        QPost post = QPost.post;
        return sort.equals("title") ? post.title.desc() : post.regDate.desc();
    }

    private <T extends EntityPathBase<?>> Map<Long, Long> getEntityCounts(List<Long> postIds, T entity, NumberPath<Long> postIdPath) {
        return jpaQueryFactory
                .select(postIdPath, entity.count())
                .from(entity)
                .where(postIdPath.in(postIds))
                .groupBy(postIdPath)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> Optional.ofNullable(tuple.get(1, Long.class)).orElse(0L)
                ));
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

        BooleanBuilder build() {
            return builder;
        }
    }
}