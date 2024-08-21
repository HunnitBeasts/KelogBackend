package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.postassist.dto.convert.TagInfos;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TagQueryDSLRepositoryImpl implements TagQueryDSLRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findTagNameByPostId(Long postId) {
        QTagPost tagPost = QTagPost.tagPost;
        return jpaQueryFactory
                .select(tagPost.id.tag)
                .from(tagPost)
                .where(tagPost.id.postId.eq(postId))
                .fetch();
    }

    @Override
    public List<TagInfos> findUserTagsByUserId(Long userId) {
        QPost post = QPost.post;
        QTagPost tagPost = QTagPost.tagPost;
        return jpaQueryFactory
                .select(Projections.constructor(TagInfos.class,
                        tagPost.id.tag,
                        tagPost.id.tag.count()))
                .from(post)
                .join(post.tagPosts, tagPost)
                .where(post.user.id.eq(userId))
                .groupBy(tagPost.id.tag)
                .fetch();
    }

    @Override
    public Set<String> findAllTags() {
        QTagPost tagPost = QTagPost.tagPost;
        return new HashSet<>(jpaQueryFactory
                .select(tagPost.id.tag)
                .from(tagPost)
                .distinct()
                .fetch());
    }
}
