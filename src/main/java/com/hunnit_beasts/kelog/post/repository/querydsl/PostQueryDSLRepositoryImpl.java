package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.post.dto.convert.PostInfos;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostUpdateResponseDTO;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.hunnit_beasts.kelog.post.entity.domain.QPostViewCnt;
import com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
        QPostViewCnt postViewCnt = QPostViewCnt.postViewCnt;
        return jpaQueryFactory
                .select(postViewCnt.viewCnt.sum())
                .from(postViewCnt)
                .where(postViewCnt.id.postId.eq(id))
                .fetchOne();
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

}
