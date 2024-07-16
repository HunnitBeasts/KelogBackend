package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostAddResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostPopResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;

public interface SeriesService {
    SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto);
    Long deleteSeries(Long seriesId);
    UserSeriesResponseDTO readUserSeries(Long userId);
    PostAddResponseDTO seriesAddPost(Long postId, Long seriesId);
    PostPopResponseDTO seriesPopPost(Long postId, Long seriesId);
}
