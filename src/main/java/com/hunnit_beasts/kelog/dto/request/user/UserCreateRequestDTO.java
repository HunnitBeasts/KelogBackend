package com.hunnit_beasts.kelog.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDTO {
    private String userId;
    private String nickname;
    private String password;
    private String briefIntro;
    private String email;
}
