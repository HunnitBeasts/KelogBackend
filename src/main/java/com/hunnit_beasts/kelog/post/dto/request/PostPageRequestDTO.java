package com.hunnit_beasts.kelog.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostPageRequestDTO {
    private String tagName;
    private String sort;
    private Long page;
    private Long size;
    private String search;
    private Long userId;
}
