package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.service.ValidateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class ValidateServiceImpl implements ValidateService {

    @Override
    public void userIdAndUserIdSameCheck(Long id, Long userId) {
        if(!Objects.equals(id, userId))
            throw new IllegalArgumentException(ErrorCode.NOT_SAME_USERID_ERROR.getMessage());
    }
}
