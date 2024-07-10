package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostUpdateResponseDTO;

public interface PostQueryDSLRepository {
    PostCreateResponseDTO findPostCreateResponseDTOById(Long id);
    Long findTotalViewCntById(Long id);
    PostUpdateResponseDTO findPostUpdateResponseDTOById(Long id);
    Long findMaxOrderBySeriesId(Long seriesId);
}
