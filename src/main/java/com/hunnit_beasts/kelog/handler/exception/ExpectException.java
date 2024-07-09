package com.hunnit_beasts.kelog.handler.exception;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpectException extends RuntimeException{
    private final ErrorCode errorCode;
}
