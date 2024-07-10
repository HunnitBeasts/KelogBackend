package com.hunnit_beasts.kelog.user.service;

import com.hunnit_beasts.kelog.user.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.user.dto.request.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.TokenResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.UserCreateResponseDTO;

public interface AuthService {
    TokenResponseDTO login(UserLoginRequestDTO dto);
    UserCreateResponseDTO signUp(UserCreateRequestDTO dto);
    Long withDraw(Long id);
}
