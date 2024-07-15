package com.hunnit_beasts.kelog.postassist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllTagsResponseDTO {
    private List<String> tags;
}
