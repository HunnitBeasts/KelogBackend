package com.hunnit_beasts.kelog.auth.etc;

import com.hunnit_beasts.kelog.user.enumeration.UserType;
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
