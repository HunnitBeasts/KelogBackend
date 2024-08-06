package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;

public record CommentEvent(CommentCreateResponseDTO dto) {}
