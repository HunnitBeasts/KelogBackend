package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.post.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.dto.request.post.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostViewCntResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.SeriesCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.RecentViewCreateResponseDTO;

public interface PostService {
    PostCreateResponseDTO postCreate(Long userId, PostCreateRequestDTO dto);
    Long postDelete(Long postId);
    PostLikeResponseDTO addPostLike(Long userId, PostLikeRequestDTO dto);
    PostViewCntResponseDTO plusViewCnt(Long postId);
    SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto);
    RecentViewCreateResponseDTO recentViewAdd(Long userId, Long postId);
}
