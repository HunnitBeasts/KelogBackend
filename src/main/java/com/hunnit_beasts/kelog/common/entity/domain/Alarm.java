package com.hunnit_beasts.kelog.common.entity.domain;

//import com.hunnit_beasts.kelog.common.entity.compositekey.AlarmId;

import com.hunnit_beasts.kelog.common.entity.superclass.RegEntity;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Alarm extends RegEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isCheck; //얘가 sql 예약어랑 똑같아서 테이블 생성이 안되는 오류가 생김 -> isCheck 로 변경

    @Column(nullable = false)
    private AlarmType alarmType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private User target;

    public Alarm(User user, User target, AlarmType alarmType){
        this.alarmType = alarmType;
        this.user = user;
        this.target = target;
        this.isCheck = false;
    }

}
