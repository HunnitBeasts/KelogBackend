package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.convert.TagInfos;

import java.util.List;
import java.util.Set;

public interface TagQueryDSLRepository {
    List<String> findTagNameByPostId(Long postId);
    List<TagInfos> findUserTagsByUserId(Long userId);
    Set<String> findAllTags();
}
