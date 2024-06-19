package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;

public interface CommentService {
    CommentCreateResponseDTO commentCreate(Long userId, CommentCreateRequestDTO dto);
}
