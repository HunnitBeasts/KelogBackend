package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;

import com.hunnit_beasts.kelog.postassist.dto.response.SeriesReadResponseDTO;

public interface SeriesQueryDSLRepository {
    Long findMaxOrderBySeriesId(Long seriesId);
    SeriesReadResponseDTO findSeriesReadResponseDTOById(Long seriesId);
    UserSeriesResponseDTO findUserSeriesResponseDTOByUserId(Long userId);
}
