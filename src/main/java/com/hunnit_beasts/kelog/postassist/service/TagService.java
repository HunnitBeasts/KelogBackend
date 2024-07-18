package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.postassist.dto.response.TagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserTagsResponseDTO;

import java.util.List;
import java.util.Set;

public interface TagService {
    void createTagPost(List<String> tags, Post createdPost);
    TagsResponseDTO allTags();
    void removeDeletedTags(Long postId, Set<String> existingTags, Set<String> newTags);
    void addNewTags(Post post, Set<String> existingTags, Set<String> newTags);
    void removeUnusedTags();
    Set<String> getExistingTags(Long postId);
    UserTagsResponseDTO userTags(Long userId);
}
