package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.postassist.dto.response.AllTagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.TagCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.TagPost;

import java.util.List;

public interface TagService {
    void createTagPost(List<String> tags, Post createdPost);
    AllTagsResponseDTO allTags();
}
