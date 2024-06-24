package com.hunnit_beasts.kelog.handler;

import com.hunnit_beasts.kelog.etc.ErrorResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static com.hunnit_beasts.kelog.enumeration.system.ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR;
import static com.hunnit_beasts.kelog.enumeration.system.ErrorCode.NO_USER_DATA_ERROR;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedException() {
        ErrorResponseDTO dto = new ErrorResponseDTO(NOT_SUPPORTED_ENDPOINT_ERROR);
        log.error("{} {}", dto.getStatus(), dto.getErrorMessage());

        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException() {
        ErrorResponseDTO dto = new ErrorResponseDTO(NO_USER_DATA_ERROR);
        log.error("{} {}", dto.getStatus(), dto.getErrorMessage());

        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException() {
        log.error("{} {}",String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),"오류가 발생하였습니다!");

        return new ResponseEntity<>("예외 발생", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
