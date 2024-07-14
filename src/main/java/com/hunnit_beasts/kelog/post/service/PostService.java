package com.hunnit_beasts.kelog.post.service;

import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostUpdateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.*;

public interface PostService {
    PostCreateResponseDTO postCreate(Long userId, PostCreateRequestDTO dto);
    Long postDelete(Long postId);
    PostLikeResponseDTO addPostLike(Long userId, PostLikeRequestDTO dto);
    PostViewCntResponseDTO plusViewCnt(Long postId);
    PostLikeResponseDTO deletePostLike(Long userId, Long postId);
    RecentViewCreateResponseDTO recentViewAdd(Long userId, Long postId);
    PostUpdateResponseDTO postUpdate(Long postId, PostUpdateRequestDTO dto);
}
