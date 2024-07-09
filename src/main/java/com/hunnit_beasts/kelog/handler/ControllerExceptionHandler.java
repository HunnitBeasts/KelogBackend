package com.hunnit_beasts.kelog.handler;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.etc.ErrorResponseDTO;
import com.hunnit_beasts.kelog.handler.exception.ExpectException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    private void LogError(Exception e){
        int status;
        String message;
        String stackTrace;

        if(e instanceof ExpectException){
            status = ((ExpectException) e).getErrorCode().getStatus();
            message = ((ExpectException) e).getErrorCode().getMessage();
        }
        else if(e instanceof UnsupportedOperationException){
            status = ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR.getStatus();
            message = ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR.getMessage();
        }
        else {
            status = ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR.getStatus();
            message = ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR.getMessage();
        }
        stackTrace = Arrays.toString(e.getStackTrace());
        log.error("status: {}, message: {}, stack trace: {}",status,message,stackTrace);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedOperationException(UnsupportedOperationException e) {
        LogError(e);
        ErrorResponseDTO dto = new ErrorResponseDTO(ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(ExpectException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpectException(ExpectException e) {
        LogError(e);
        ErrorResponseDTO dto = new ErrorResponseDTO(e.getErrorCode());
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        LogError(e);
        ErrorResponseDTO dto = new ErrorResponseDTO(ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

}
