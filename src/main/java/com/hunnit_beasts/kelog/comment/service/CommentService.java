package com.hunnit_beasts.kelog.comment.service;

import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.request.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentDeleteResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;

public interface CommentService {
    CommentCreateResponseDTO commentCreate(Long userId, CommentCreateRequestDTO dto);
    CommentDeleteResponseDTO commentDelete(Long commentId);
    CommentUpdateResponseDTO commentUpdate(Long commentId, CommentUpdateRequestDTO dto);
}
