package com.hunnit_beasts.kelog.handler;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.etc.ErrorResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static com.hunnit_beasts.kelog.enumeration.system.ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR;
import static com.hunnit_beasts.kelog.enumeration.system.ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    private final List<ErrorCode> errorCodes;

    private String errorCode;
    private String message = "";

    public ControllerExceptionHandler(){
        this.errorCodes = List.of(ErrorCode.values());
    }

    private void splitCodeAndMessage(String errorMessage){
            if(errorMessage.contains("&")){
                String[] errorList = errorMessage.split("&");
                this.errorCode = errorList[0];
                this.message = errorList[1].trim();
            }
            else {
                this.errorCode = errorMessage;
            }
    }

    private ErrorCode getErrorCode(String code){
        for(ErrorCode errorCode : this.errorCodes){
            if(errorCode.getCode().equals(code.trim())){
                return errorCode;
            }
        }
        return OCCUR_UNKNOWN_TYPE_ERROR;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e) {

        splitCodeAndMessage(e.getMessage());
        ErrorCode code = getErrorCode(errorCode);
        ErrorResponseDTO dto;

        if(message.isBlank()){
            dto = new ErrorResponseDTO(code);
        }else{
            dto = new ErrorResponseDTO(code,message);
        }

        log.error("status : {} message : {}", dto.getStatus(), dto.getErrorMessage());

        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedOperationException() {
        ErrorResponseDTO dto = new ErrorResponseDTO(NOT_SUPPORTED_ENDPOINT_ERROR);
        log.error("{} {}",dto.getStatus(),dto.getErrorMessage());

        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException() {
        ErrorResponseDTO dto = new ErrorResponseDTO(OCCUR_UNKNOWN_TYPE_ERROR);
        log.error("{} {}",dto.getStatus(),dto.getErrorMessage()+" type: Exception");

        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }
}
