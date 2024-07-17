package com.hunnit_beasts.kelog.user.entity.domain;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.entity.compositekey.SocialInfoId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Social {

    @EmbeddedId
    private SocialInfoId id;

    @Column(length = 512,nullable = false)
    private String link;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    public Social changeUrl(String link){
        this.link = link;
        return this;
    }

    public Social(SocialInfos socialInfos,User user){
        this.id = new SocialInfoId(user.getId(), socialInfos.getSocialType());
        this.link = socialInfos.getUrl();
        this.user = user;
    }

}
