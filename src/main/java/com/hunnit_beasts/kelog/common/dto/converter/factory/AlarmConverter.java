package com.hunnit_beasts.kelog.common.dto.converter.factory;

import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;

public interface AlarmConverter {
    AlarmReadResponseDTO convert(Alarm alarm);
}
