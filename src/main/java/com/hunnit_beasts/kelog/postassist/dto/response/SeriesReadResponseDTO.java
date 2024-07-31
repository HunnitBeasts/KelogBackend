package com.hunnit_beasts.kelog.postassist.dto.response;

import com.hunnit_beasts.kelog.postassist.dto.convert.SeriesPostInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SeriesReadResponseDTO {
    private String seriesName;
    private List<SeriesPostInfo> posts;
}
