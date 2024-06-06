package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.user.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.TokenResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public TokenResponseDTO login(UserLoginRequestDTO dto) {
        User loginUser = userJpaRepository.findByUserId(dto.getUserId())
                .orElseThrow(IllegalArgumentException::new);

        if(encoder.matches(dto.getPassword(), loginUser.getPassword()))
            return new TokenResponseDTO(jwtUtil.createToken(loginUser.entityToCustomUserInfoDTO()));
        else
            return new TokenResponseDTO("");
    }
}
