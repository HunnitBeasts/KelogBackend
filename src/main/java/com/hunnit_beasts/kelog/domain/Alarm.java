package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.AlarmId;
import jakarta.persistence.*;
import com.hunnit_beasts.kelog.domain.User;

import java.time.LocalDateTime;

@Entity
public class Alarm {

    @EmbeddedId
    private AlarmId id;

    @Column(nullable = false)
    private LocalDateTime regDate; //regDate 하나만 쓰므로 baseEntity 사용하지 않음

    @Column(nullable = false)
    private Boolean isCheck; //얘가 sql 예약어랑 똑같아서 테이블 생성이 안되는 오류가 생김 -> isCheck 로 변경

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("targetId")
    @ManyToOne
    @JoinColumn(name = "target_id")
    private User target;

}
