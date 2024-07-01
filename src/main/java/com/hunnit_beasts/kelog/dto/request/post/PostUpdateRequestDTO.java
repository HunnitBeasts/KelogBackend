package com.hunnit_beasts.kelog.dto.request.post;

import com.hunnit_beasts.kelog.enumeration.types.PostType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostUpdateRequestDTO {
    private String title;
    private PostType type;
    private String thumbImage;
    private Boolean isPublic;
    private String shortContent;
    private String url;
    private String content;
}
