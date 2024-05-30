package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.AlarmId;
import com.hunnit_beasts.kelog.entity.superclass.RegEntity;
import jakarta.persistence.*;

@Entity
public class Alarm extends RegEntity {

    @EmbeddedId
    private AlarmId id;

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
