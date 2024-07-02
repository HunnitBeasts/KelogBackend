package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.user.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.TokenResponseDTO;
import com.hunnit_beasts.kelog.dto.response.user.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.repository.querydsl.UserQueryDSLRepository;
import com.hunnit_beasts.kelog.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserJpaRepository userJpaRepository;
    private final UserQueryDSLRepository userQueryDSLRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public TokenResponseDTO login(UserLoginRequestDTO dto) {
        User loginUser = userJpaRepository.findByUserId(dto.getUserId())
                .orElseThrow(()-> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getCode()));

        if(encoder.matches(dto.getPassword(), loginUser.getPassword()))
            return new TokenResponseDTO(jwtUtil.createToken(loginUser.entityToCustomUserInfoDTO()));
        else
            return new TokenResponseDTO("");
    }

    @Override
    public UserCreateResponseDTO signUp(UserCreateRequestDTO dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        User createUserEntity = new User(dto);
        User signUpUser = userJpaRepository.save(createUserEntity);
        return userQueryDSLRepository.findUserCreateResponseDTOById(signUpUser.getId());
    }

    @Override
    public Long withDraw(Long id) {
        userJpaRepository.deleteById(id);
        return id;
    }

}
