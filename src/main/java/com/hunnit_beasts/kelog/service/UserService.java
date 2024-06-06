package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.user.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.TokenResponseDTO;

public interface UserService {
    TokenResponseDTO login(UserLoginRequestDTO dto);
}
