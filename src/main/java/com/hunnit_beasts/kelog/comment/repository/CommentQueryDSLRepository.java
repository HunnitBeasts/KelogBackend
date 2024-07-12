package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;

public interface CommentQueryDSLRepository {

    CommentCreateResponseDTO findCommentCreateResponseDTOById(Long id);
    CommentUpdateResponseDTO findCommentUpdateResponseDTOById(Long id);
}
