package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.postassist.dto.convert.TagInfos;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTag;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.hunnit_beasts.kelog.postassist.entity.domain.Tag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagQueryDSLRepositoryImpl implements TagQueryDSLRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tag> findUnusedTags() {
        QTag tag = QTag.tag;
        QTagPost tagPost = QTagPost.tagPost;

        return jpaQueryFactory
                .selectFrom(tag)
                .leftJoin(tagPost)
                .on(tagPost.tag.eq(tag))
                .where(tagPost.tag.isNull())
                .fetch();
    }

    @Override
    public List<String> findTagNameByPostId(Long postId) {
        QTagPost tagPost = QTagPost.tagPost;
        return jpaQueryFactory
                .select(tagPost.tag.tagName)
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
                        tagPost.tag.tagName,
                        tagPost.tag.tagName.count()))
                .from(post)
                .join(post.tagPosts, tagPost)
                .where(post.user.id.eq(userId))
                .groupBy(tagPost.tag.tagName)
                .fetch();
    }
}
