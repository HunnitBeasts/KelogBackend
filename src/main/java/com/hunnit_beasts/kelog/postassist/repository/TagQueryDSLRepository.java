package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.convert.TagInfos;
import com.hunnit_beasts.kelog.postassist.entity.domain.Tag;

import java.util.List;

public interface TagQueryDSLRepository {
    List<Tag> findUnusedTags();
    List<String> findTagNameByPostId(Long postId);
    List<TagInfos> findUserTagsByUserId(Long userId);
}
