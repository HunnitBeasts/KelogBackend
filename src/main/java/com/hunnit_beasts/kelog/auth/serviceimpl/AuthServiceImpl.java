package com.hunnit_beasts.kelog.auth.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.request.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.response.TokenResponseDTO;
import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.repository.querydsl.UserQueryDSLRepository;
import com.hunnit_beasts.kelog.auth.service.AuthService;
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
                .orElseThrow(()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

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
