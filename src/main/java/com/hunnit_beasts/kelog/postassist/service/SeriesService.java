package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostAddResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesCreateResponseDTO;

public interface SeriesService {
    SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto);
    Long deleteSeries(Long seriesId);
    PostAddResponseDTO seriesAddPost(Long postId, Long seriesId);
}
