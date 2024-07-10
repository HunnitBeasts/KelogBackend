package com.hunnit_beasts.kelog.auth.service;

import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.request.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.response.TokenResponseDTO;
import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;

public interface AuthService {
    TokenResponseDTO login(UserLoginRequestDTO dto);
    UserCreateResponseDTO signUp(UserCreateRequestDTO dto);
    Long withDraw(Long id);
}
