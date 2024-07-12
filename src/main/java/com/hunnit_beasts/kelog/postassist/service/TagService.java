package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.postassist.dto.request.TagCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.TagCreateResponseDTO;

public interface TagService {
    TagCreateResponseDTO createTag(TagCreateRequestDTO dto);
}
