package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.post.dto.info.PostOrderInfo;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostUpdateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostViewCountResponseDTO;

import java.util.List;

public interface PostQueryDSLRepository {
    PostCreateResponseDTO findPostCreateResponseDTOById(Long id);
    Long findTotalViewCntById(Long id);
    PostUpdateResponseDTO findPostUpdateResponseDTOById(Long id);
    PostViewCountResponseDTO findViewCntInfosById(Long id);
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    Long countByPostId(Long postId);
    List<String> findTagsByPostId(Long postId);
    String findContentByPostId(Long postId);
    PostOrderInfo findNextPostByUser(Long userId, Long postId);
    PostOrderInfo findPreviousPostByUser(Long userId, Long postId);
}
