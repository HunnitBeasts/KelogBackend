package com.hunnit_beasts.kelog.common.dto.converter;

import com.hunnit_beasts.kelog.common.dto.converter.factory.AlarmConverterFactory;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AlarmDtoConverter {

   private final AlarmConverterFactory alarmConverterFactory;

   public AlarmReadResponseDTO convert(Alarm alarm) {
       return alarmConverterFactory.getAlarmConverter(alarm.getAlarmType()).convert(alarm);
   }

}
