package com.hunnit_beasts.kelog.postassist.dto.response;

import com.hunnit_beasts.kelog.postassist.dto.convert.UserSeriesInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserSeriesResponseDTO {
    private List<UserSeriesInfos> seriesList;
}
