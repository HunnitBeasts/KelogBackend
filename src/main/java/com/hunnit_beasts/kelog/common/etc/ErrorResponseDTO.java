package com.hunnit_beasts.kelog.common.etc;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.manager.ErrorMessageManager;
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


    public ErrorResponseDTO(ErrorCode errorCode, ErrorMessageManager errorMessageManager){
        this.status = errorCode.getStatus();
        this.errorMessage = errorMessageManager.getMessages(errorCode.name());
        this.time = LocalDateTime.now();
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.resolve(status);
    }

    @Override
    public ProblemDetail getBody() { return null; }


}
