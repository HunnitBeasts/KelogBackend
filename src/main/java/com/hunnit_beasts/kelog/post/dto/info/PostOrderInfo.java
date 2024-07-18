package com.hunnit_beasts.kelog.post.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostOrderInfo {
    private String title;
    private Long postId;
    private String url;
}
