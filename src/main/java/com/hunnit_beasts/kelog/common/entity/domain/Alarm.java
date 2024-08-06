package com.hunnit_beasts.kelog.common.entity.domain;

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

    @Setter
    @Column(nullable = false)
    private Boolean isCheck;

    @Column(nullable = false)
    private AlarmType alarmType;

    @Column
    private Long targetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Alarm(User user, Long targetId, AlarmType alarmType){
        this.alarmType = alarmType;
        this.user = user;
        this.targetId = targetId;
        this.isCheck = false;
    }

}
