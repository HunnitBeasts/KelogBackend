package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.response.SeriesReadResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost;

import java.util.List;

public interface SeriesQueryDSLRepository {
    Long findMaxOrderBySeriesId(Long seriesId);
    SeriesReadResponseDTO findSeriesReadResponseDTOById(Long seriesId);
    UserSeriesResponseDTO findUserSeriesResponseDTOByUserId(Long userId);
    List<SeriesPost> updateOrder(Long seriesId, List<Long> posts);
}
