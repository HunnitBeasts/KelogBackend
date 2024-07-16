package com.hunnit_beasts.kelog.user.dto.response;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialUpdateResponseDTO {
    private Long userId;
    private List<SocialInfos> socials;
}
