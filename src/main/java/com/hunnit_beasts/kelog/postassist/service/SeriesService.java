package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesUpdateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.*;

public interface SeriesService {
    SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto);
    SeriesReadResponseDTO readSeries(Long seriesId);
    SeriesReadResponseDTO updateSeries(Long seriesId, SeriesUpdateRequestDTO dto);
    Long deleteSeries(Long seriesId);
    UserSeriesResponseDTO readUserSeries(Long userId);
    PostAddResponseDTO seriesAddPost(Long postId, Long seriesId);
    PostPopResponseDTO seriesPopPost(Long postId, Long seriesId);
}
