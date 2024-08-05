package com.hunnit_beasts.kelog.postassist.dto.response;

import com.hunnit_beasts.kelog.postassist.dto.convert.TagInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserTagsResponseDTO {
    private Long postCount;
    private List<TagInfos> tags;
}
