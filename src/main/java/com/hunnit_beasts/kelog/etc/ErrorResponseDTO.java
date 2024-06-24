package com.hunnit_beasts.kelog.etc;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.net.URI;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDTO implements ErrorResponse {

    private final int status;
    private final String errorMessage;
    private final LocalDateTime time;

    public ErrorResponseDTO(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.errorMessage = errorCode.getMessage();
        this.time = LocalDateTime.now();
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.resolve(status);
    }

    @Override
    public ProblemDetail getBody() { return null; }


}
