package com.hunnit_beasts.kelog.user.dto.response;

import com.hunnit_beasts.kelog.user.dto.convert.FollowerInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowerReadResponseDTO {
    private Long followerCount;
    private List<FollowerInfos> userInfos;
}
