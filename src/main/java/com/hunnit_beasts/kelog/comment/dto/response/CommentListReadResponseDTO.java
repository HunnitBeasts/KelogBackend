package com.hunnit_beasts.kelog.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentListReadResponseDTO {
    List<CommentReadDTO> results;
    Long count;
}
