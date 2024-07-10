package com.hunnit_beasts.kelog.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.etc.ErrorResponseDTO;
import com.hunnit_beasts.kelog.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.manager.ErrorMessageManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Log4j2
public class FilterExceptionHandler {

    private final ErrorMessageManager errorMessageManager;
    private final ObjectMapper mapper;

    private void logError(Exception e){
        int status;
        String message;
        String stackTrace;

        if(e instanceof ExpectException expectException){
            status = expectException.getErrorCode().getStatus();
            message = errorMessageManager.getMessages(expectException.getErrorCode().toString());
        }
        else if(e instanceof UnsupportedOperationException ){
            status = ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR.getStatus();
            message = errorMessageManager.getMessages(ErrorCode.NOT_SUPPORTED_ENDPOINT_ERROR.name());
        }
        else {
            status = ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR.getStatus();
            message = errorMessageManager.getMessages(ErrorCode.OCCUR_UNKNOWN_TYPE_ERROR.name());
        }
        stackTrace = Arrays.toString(e.getStackTrace());
        log.error("status: {}, message: {}, stack trace: {}",status,message,stackTrace);
    }

    public void handleExpectException(HttpServletResponse response, ExpectException e) throws IOException {
        logError(e);

        ErrorResponseDTO dto = new ErrorResponseDTO(e.getErrorCode(),errorMessageManager);
        String responseBody = mapper.writeValueAsString(dto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(e.getErrorCode().getStatus());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
