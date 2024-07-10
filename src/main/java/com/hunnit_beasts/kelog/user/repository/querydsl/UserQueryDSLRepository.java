package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.user.dto.response.UserCreateResponseDTO;

public interface UserQueryDSLRepository {

    UserCreateResponseDTO findUserCreateResponseDTOById(Long id);
}
