package com.hunnit_beasts.kelog.user.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDTO {
    private String userId;
    private String nickname;
    @Setter
    private String password;
    private String briefIntro;
    private String email;
}
