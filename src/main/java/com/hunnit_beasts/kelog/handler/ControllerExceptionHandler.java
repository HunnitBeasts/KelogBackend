package com.hunnit_beasts.kelog.handler;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.etc.ErrorResponseDTO;
import com.hunnit_beasts.kelog.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.manager.ErrorMessageManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class ControllerExceptionHandler {

    private final ErrorMessageManager errorMessageManager;

    private void logError(Exception e){
        int status;
        String message;
        String stackTrace;

        if(e instanceof ExpectException expectException){
            status = expectException.getErrorCode().getStatus();
            message = errorMessageManager.getMessages(expectException.getErrorCode().toString());
        } else if(e instanceof UnsupportedOperationException ){
            status = ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR.getStatus();
            message = errorMessageManager.getMessages(ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR.name());
        } else {
            status = ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR.getStatus();
            message = errorMessageManager.getMessages(ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR.name());
        }
        stackTrace = Arrays.toString(e.getStackTrace());
        log.error("status: {}, message: {}, stack trace: {}",status,message,stackTrace);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedOperationException(UnsupportedOperationException e) {
        logError(e);
        ErrorResponseDTO dto = new ErrorResponseDTO(ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR,errorMessageManager);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(ExpectException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpectException(ExpectException e) {
        logError(e);
        ErrorResponseDTO dto = new ErrorResponseDTO(e.getErrorCode(),errorMessageManager);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        logError(e);
        ErrorResponseDTO dto = new ErrorResponseDTO(ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR,errorMessageManager);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

}
