package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentReadResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;

import java.util.List;

public interface CommentQueryDSLRepository {

    CommentCreateResponseDTO findCommentCreateResponseDTOById(Long id);
    CommentUpdateResponseDTO findCommentUpdateResponseDTOById(Long id);
    List<CommentReadResponseDTO> findCommentReadResponseDTOsByPostId(Long postId);
    CommentReadResponseDTO findCommentReadResponseDTOByCommentId(Long commentId);
}
