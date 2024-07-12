package com.hunnit_beasts.kelog.postassist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateRequestDTO {
    private String tagName;
}
