package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.tag.TagCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.tag.TagCreateResponseDTO;

public interface TagService {
    TagCreateResponseDTO createTag(TagCreateRequestDTO dto);
}
