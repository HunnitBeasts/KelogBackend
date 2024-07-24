package com.hunnit_beasts.kelog.postassist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesUpdateRequestDTO {
    private String seriesName;
    private List<Long> posts;
}
