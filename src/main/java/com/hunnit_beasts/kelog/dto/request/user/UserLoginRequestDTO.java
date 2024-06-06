package com.hunnit_beasts.kelog.dto.request.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginRequestDTO {
    private final String userId;
    private final String password;
}
