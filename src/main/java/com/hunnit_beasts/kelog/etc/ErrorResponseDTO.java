package com.hunnit_beasts.kelog.etc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ErrorResponseDTO implements ErrorResponse {

    private final int status;
    private final String errorMessage;
    private final LocalDateTime time;

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.resolve(status);
    }

    @Override
    public ProblemDetail getBody() {
        return null;
    }
}
