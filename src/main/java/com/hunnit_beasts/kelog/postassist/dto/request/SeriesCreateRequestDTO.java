package com.hunnit_beasts.kelog.postassist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeriesCreateRequestDTO {
    private String seriesName;
    private String url;
}
