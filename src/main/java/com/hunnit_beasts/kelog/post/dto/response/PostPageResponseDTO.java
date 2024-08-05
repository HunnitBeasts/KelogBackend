package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.dto.convert.PostPageConvert;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostPageResponseDTO {
    private Long count;
    private List<PostPageConvert> posts;
}
