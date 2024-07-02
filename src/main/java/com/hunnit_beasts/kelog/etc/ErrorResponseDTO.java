package com.hunnit_beasts.kelog.etc;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO implements ErrorResponse {

    private final int status;
    @Setter
    private String errorMessage;
    private final LocalDateTime time;

    public ErrorResponseDTO(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.errorMessage = errorCode.getMessage();
        this.time = LocalDateTime.now();
    }

    public ErrorResponseDTO(ErrorCode errorCode,String errorMessage){
        this.status = errorCode.getStatus();
        this.errorMessage = errorMessage;
        this.time = LocalDateTime.now();
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.resolve(status);
    }

    @Override
    public ProblemDetail getBody() { return null; }


}
