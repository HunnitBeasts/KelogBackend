package com.hunnit_beasts.kelog.repository.querydsl;

import com.hunnit_beasts.kelog.dto.response.post.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostUpdateResponseDTO;

public interface PostQueryDSLRepository {
    PostCreateResponseDTO findPostCreateResponseDTOById(Long id);
    Long findTotalViewCntById(Long id);
    PostUpdateResponseDTO findPostUpdateResponseDTOById(Long id);
    Long findMaxOrderBySeriesId(Long seriesId);
}
