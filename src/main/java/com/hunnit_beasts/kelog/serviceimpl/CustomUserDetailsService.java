package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.convert.user.CustomUserDetails;
import com.hunnit_beasts.kelog.dto.info.user.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User loginUser = userJpaRepository.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getCode()));
        return new CustomUserDetails(mapper.map(loginUser, CustomUserInfoDTO.class));
    }

    public CustomUserDetails loadCustomUserByUsername(Long id) throws UsernameNotFoundException {
        User loginUser = userJpaRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getCode()));
        return new CustomUserDetails(mapper.map(loginUser, CustomUserInfoDTO.class));
    }
}
