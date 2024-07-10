package com.hunnit_beasts.kelog.post.service;

import com.hunnit_beasts.kelog.post.dto.request.TagCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.TagCreateResponseDTO;

public interface TagService {
    TagCreateResponseDTO createTag(TagCreateRequestDTO dto);
}
