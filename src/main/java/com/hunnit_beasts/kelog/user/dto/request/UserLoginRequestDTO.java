package com.hunnit_beasts.kelog.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO {
    private String userId;
    private String password;
}
