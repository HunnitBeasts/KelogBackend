package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.user.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.TokenResponseDTO;
import com.hunnit_beasts.kelog.dto.response.user.UserCreateResponseDTO;

public interface AuthService {
    TokenResponseDTO login(UserLoginRequestDTO dto);
    UserCreateResponseDTO signUp(UserCreateRequestDTO dto);
}
