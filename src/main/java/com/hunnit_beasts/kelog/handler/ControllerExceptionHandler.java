package com.hunnit_beasts.kelog.handler;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.etc.ErrorResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    private final List<ErrorCode> errorCodes;

    public ControllerExceptionHandler() {
        this.errorCodes = List.of(ErrorCode.values());
    }

    private boolean isErrorCode(String errorMessage) {
        return errorMessage.startsWith("CODE");
    }

    private void logErrorResponse(ErrorResponseDTO dto) {
        log.error("status : {} message : {}",dto.getStatus(), dto.getErrorMessage());
    }

    private ErrorCode findErrorCode(String code) {
        return errorCodes.stream()
                .filter(errorCode -> errorCode.getCode().equals(code.trim()))
                .findFirst()
                .orElse(ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR);
    }

    private ErrorResponseDTO createErrorResponseFromMessage(String errorMessage) {
        if (!isErrorCode(errorMessage))
            return new ErrorResponseDTO(ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR, errorMessage);
        else {
            if (errorMessage.length() == 10)
                return new ErrorResponseDTO(findErrorCode(errorMessage));
            String code = errorMessage.substring(0, 10);
            String message = errorMessage.substring(10);
            ErrorCode errorCode = findErrorCode(code);
            return new ErrorResponseDTO(errorCode, message);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponseDTO dto = createErrorResponseFromMessage(e.getMessage());
        logErrorResponse(dto);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedOperationException() {
        ErrorResponseDTO dto = new ErrorResponseDTO(ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR);
        logErrorResponse(dto);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        ErrorResponseDTO dto = createErrorResponseFromMessage(e.getMessage());
        logErrorResponse(dto);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }
}
