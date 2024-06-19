package com.hunnit_beasts.kelog.repository.querydsl;

import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;

public interface CommentQueryDSLRepository {

    CommentCreateResponseDTO findCommentCreateResponseDTOById(Long id);
}
