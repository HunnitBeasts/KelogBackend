package com.hunnit_beasts.kelog.dto.request.post;

import com.hunnit_beasts.kelog.enumeration.types.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDTO {
    private String title;
    private PostType type;
    private String thumbImage;
    private Boolean isPublic;
    private String shortContent;
    private String url;
    private String content;
}
