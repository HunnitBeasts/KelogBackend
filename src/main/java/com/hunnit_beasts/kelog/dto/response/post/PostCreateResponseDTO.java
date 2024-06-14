package com.hunnit_beasts.kelog.dto.response.post;

import com.hunnit_beasts.kelog.enumeration.types.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private PostType type;
    private String thumbImage;
    private Boolean disclosure;
    private String shortContent;
    private String url;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;


}
