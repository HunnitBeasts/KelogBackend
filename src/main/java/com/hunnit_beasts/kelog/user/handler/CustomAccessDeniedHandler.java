package com.hunnit_beasts.kelog.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.common.etc.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Log4j2(topic = "FORBIDDEN_EXCEPTION_HANDLER")
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("No Authorities : ", accessDeniedException);

        ErrorResponseDTO errorResponseDTO =
                new ErrorResponseDTO(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage(), LocalDateTime.now());

        String responseBody = mapper.writeValueAsString(errorResponseDTO);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
