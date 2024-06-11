package com.hunnit_beasts.kelog.repository.querydsl;

import com.hunnit_beasts.kelog.dto.response.user.UserCreateResponseDTO;

public interface UserQueryDSLRepository {

    UserCreateResponseDTO findUserCreateResponseDTOById(Long id);
}
