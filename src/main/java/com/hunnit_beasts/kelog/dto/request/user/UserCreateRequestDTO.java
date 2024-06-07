package com.hunnit_beasts.kelog.dto.request.user;

import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.types.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDTO {
    private String userId;
    private String password;
    private String nickname;
    private String thumbImage;
    private String briefIntro;
    private String email;
    private Boolean emailSetting;
    private Boolean alarmSetting;
    private String kelogName;

    public User UserCreateDTOToEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .thumbImage(thumbImage)
                .briefIntro(briefIntro)
                .email(email)
                .emailSetting(emailSetting)
                .alarmSetting(alarmSetting)
                .kelogName(kelogName)
                .userType(UserType.USER)
                .build();
    }
}
