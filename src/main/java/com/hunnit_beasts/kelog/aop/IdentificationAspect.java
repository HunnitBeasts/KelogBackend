package com.hunnit_beasts.kelog.aop;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.service.AuthenticatedService;
import com.hunnit_beasts.kelog.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class IdentificationAspect {

    private final ValidateService validateService;
    private final AuthenticatedService authenticatedService;
    // 새로운 아이디 종류가 추가 된다면 yaml파일에 추가 하시면 됩니다.
    @Value("${arg-types}")
    private final Set<String> argTypes;

    @Before("@annotation(com.hunnit_beasts.kelog.aop.Identification)")
    public void identification(JoinPoint joinPoint) {
        Map<String, Long> parameters = extractParameters(joinPoint);
        Long id = parameters.get("id");

        if (parameters.containsKey("userId"))
            validateUserId(id, parameters.get("userId"));
        else if (parameters.containsKey("postId"))
            validatePostId(id, parameters.get("postId"));
        else if (parameters.containsKey("commentId"))
            validateCommentId(id, parameters.get("commentId"));
        else if (parameters.containsKey("seriesId"))
            validateSeriesId(id, parameters.get("seriesId"));
        else
            throw new ExpectException(ErrorCode.NO_TARGET_TYPE_ERROR);
    }

    private Map<String, Long> extractParameters(JoinPoint joinPoint) {
        Map<String, Long> parameters = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0)
            throw new ExpectException(ErrorCode.NO_PARAMETER_ERROR);

        for (int i = 0; i < parameterNames.length; i++)
            if ("authentication".equals(parameterNames[i])){
                Long id = authenticatedService.getId((Authentication) args[i]);
                parameters.put("id", id);
            } else if(argTypes.contains(parameterNames[i]))
                parameters.put(parameterNames[i], (Long) args[i]);
        return parameters;
    }

    private void validateUserId(Long id, Long userId) {
        validateService.userIdAndUserIdSameCheck(id, userId);
    }

    private void validatePostId(Long id, Long postId) {
        validateService.userIdAndPostIdSameCheck(id, postId);
    }

    private void validateCommentId(Long id, Long commentId) {
        validateService.userIdAndCommentIdSameCheck(id, commentId);
    }

    private void validateSeriesId(Long id, Long seriesId) {
        validateService.userIdAndSeriesIdSameCheck(id, seriesId);
    }

}
