package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record CommentEvent(CommentCreateResponseDTO dto) {}
