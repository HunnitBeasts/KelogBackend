package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.comment.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentUpdateResponseDTO;

public interface CommentService {
    CommentCreateResponseDTO commentCreate(Long userId, CommentCreateRequestDTO dto);
    CommentUpdateResponseDTO commentUpdate(Long commentId, CommentUpdateRequestDTO dto);
}
