package com.hunnit_beasts.kelog.post.dto.request;

import com.hunnit_beasts.kelog.post.enumeration.PostType;
import lombok.*;

import java.util.List;

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
    @Setter
    private String url;
    private String content;
    private List<String> tags;
}
