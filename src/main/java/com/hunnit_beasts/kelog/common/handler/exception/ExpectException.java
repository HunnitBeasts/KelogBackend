package com.hunnit_beasts.kelog.common.handler.exception;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpectException extends RuntimeException{
    private final ErrorCode errorCode;
}
