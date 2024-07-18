package com.hunnit_beasts.kelog.postassist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class TagsResponseDTO {
    private Set<String> tags;
}
