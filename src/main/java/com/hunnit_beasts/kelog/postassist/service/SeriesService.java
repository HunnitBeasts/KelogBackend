package com.hunnit_beasts.kelog.postassist.service;

import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostAddResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostPopResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesReadResponseDTO;

public interface SeriesService {
    SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto);
    SeriesReadResponseDTO readSeries(Long seriesId);
    Long deleteSeries(Long seriesId);
    PostAddResponseDTO seriesAddPost(Long postId, Long seriesId);
    PostPopResponseDTO seriesPopPost(Long postId, Long seriesId);
}
