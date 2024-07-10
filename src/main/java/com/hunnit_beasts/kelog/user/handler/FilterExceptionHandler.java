package com.hunnit_beasts.kelog.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.common.etc.ErrorResponseDTO;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.manager.ErrorMessageManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class FilterExceptionHandler {

    private final ErrorMessageManager errorMessageManager;
    private final ObjectMapper mapper;

    public void handleExpectException(HttpServletResponse response, ExpectException e) throws IOException {
        log.error("status: {}, message: {}, stack trace: {}"
                ,e.getErrorCode().getStatus()
                ,errorMessageManager.getMessages(e.getErrorCode().name())
                ,e.getStackTrace());

        ErrorResponseDTO dto = new ErrorResponseDTO(e.getErrorCode(),errorMessageManager);
        String responseBody = mapper.writeValueAsString(dto);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(e.getErrorCode().getStatus());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
