package com.hunnit_beasts.kelog.aop;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.service.AuthenticatedService;
import com.hunnit_beasts.kelog.service.ValidateService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class IdentificationAspect {

    private final ValidateService validateService;
    private final AuthenticatedService authenticatedService;

    @Value("${arg-types}")
    private final Set<String> argTypes;

    @Before("@annotation(com.hunnit_beasts.kelog.aop.Identification)")
    public void identification(JoinPoint joinPoint) {
        Map<String, Long> parameters = extractParameters(joinPoint);
        Long id = parameters.remove("id");  // id를 가져오고 map에서 제거

        if (parameters.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_TARGET_TYPE_ERROR.getMessage());
        }

        for (Map.Entry<String, Long> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();

            switch (key) {
                case "userId" -> validateUserId(id, value);
                case "postId" -> validatePostId(id, value);
                case "commentId" -> validateCommentId(id, value);
                case "seriesId" -> validateSeriesId(id, value);
                default -> throw new IllegalArgumentException("Unexpected parameter: " + key);
            }
        }
    }

    private Map<String, Long> extractParameters(JoinPoint joinPoint) {
        Map<String, Long> parameters = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0)
            throw new IllegalArgumentException(ErrorCode.NO_PARAMETER_ERROR.getMessage());

        for (int i = 0; i < parameterNames.length; i++) {
            if ("authentication".equals(parameterNames[i])) {
                Long id = authenticatedService.getId((Authentication) args[i]);
                parameters.put("id", id);
            } else if (argTypes.contains(parameterNames[i])) {
                parameters.put(parameterNames[i], (Long) args[i]);
            } else if (parameterNames[i].equals("params")) {
                addParameter(parameters, args[i]);
            }
        }
        return parameters;
    }

    @SneakyThrows
    private void addParameter(Map<String, Long> parameters, Object args) {
        for (Field field : args.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(argTypes.contains(field.getName()))
                parameters.put(field.getName(), (Long) field.get(args));
        }
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
