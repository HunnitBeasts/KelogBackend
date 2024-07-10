package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.enumeration.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUpdateResponseDTO {
    private Long id;
    private String title;
    private PostType type;
    private String thumbImage;
    private Boolean isPublic;
    private String shortContent;
    private String url;
    private String content;
}
