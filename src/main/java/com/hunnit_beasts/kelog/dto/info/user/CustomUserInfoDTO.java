package com.hunnit_beasts.kelog.dto.info.user;

import com.hunnit_beasts.kelog.enumeration.types.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserInfoDTO {

    private Long id;
    private String userId;
    private String password;
    private UserType userType;

}
