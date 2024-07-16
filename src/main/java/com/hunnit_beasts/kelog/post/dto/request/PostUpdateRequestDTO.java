package com.hunnit_beasts.kelog.post.dto.request;

import com.hunnit_beasts.kelog.post.enumeration.PostType;
import lombok.*;

import java.util.List;

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
    private List<String> tags;
}
