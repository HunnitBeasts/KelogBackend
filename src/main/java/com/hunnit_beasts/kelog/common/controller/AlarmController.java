package com.hunnit_beasts.kelog.common.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/{user-id}")
    @Identification
    public ResponseEntity<List<AlarmReadResponseDTO>> alarm(@PathVariable(value = "user-id") Long userId,
                                                            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(alarmService.readAlarm(userId));
    }

    @DeleteMapping("/{user-id}")
    @Identification
    public ResponseEntity<List<Long>> deleteAllAlarm(@PathVariable(value = "user-id") Long userId,
                                                            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(alarmService.deleteAllAlarm(userId));
    }

    @PatchMapping("/{user-id}")
    @Identification
    public ResponseEntity<List<Long>> allAlarmCheck(@PathVariable(value = "user-id") Long userId,
                                                                    Authentication authentication){

        return ResponseEntity.status(HttpStatus.OK).body(alarmService.allAlarmCheck(userId));
    }

}
