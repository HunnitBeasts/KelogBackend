package com.hunnit_beasts.kelog.repository.querydsl;

import com.hunnit_beasts.kelog.dto.response.post.PostCreateResponseDTO;

public interface PostQueryDSLRepository {
    PostCreateResponseDTO findPostCreateResponseDTOById(Long id);
}
