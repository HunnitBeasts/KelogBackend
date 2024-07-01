package com.hunnit_beasts.kelog.repository.querydsl;

import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentUpdateResponseDTO;

public interface CommentQueryDSLRepository {

    CommentCreateResponseDTO findCommentCreateResponseDTOById(Long id);
    CommentUpdateResponseDTO findCommentUpdateResponseDTOById(Long id);
}
