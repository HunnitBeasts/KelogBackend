package com.hunnit_beasts.kelog.aop;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class IdentificationAspect {

    private final ValidateService validateService;
    private final JwtUtil jwtUtil;

    @Before("@annotation(com.hunnit_beasts.kelog.aop.Identification)")
    public void identification(JoinPoint joinPoint) {
        Map<String, Long> parameters = extractParameters(joinPoint);
        Long id = parameters.get("id");

        if (parameters.containsKey("userId"))
            validateUserId(id, parameters.get("userId"));
        else
            throw new IllegalArgumentException(ErrorCode.NO_TARGET_TYPE_ERROR.getMessage());
    }

    private Map<String, Long> extractParameters(JoinPoint joinPoint) {
        Map<String, Long> parameters = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0)
            throw new IllegalArgumentException(ErrorCode.NO_PARAMETER_ERROR.getMessage());

        for (int i = 0; i < parameterNames.length; i++)
            if ("token".equals(parameterNames[i])){
                String token = ((String) args[i]).substring(7);
                parameters.put("id", jwtUtil.getId(token));
            } else
                parameters.put(parameterNames[i], (Long) args[i]);

        return parameters;
    }

    private void validateUserId(Long id, Long userId) {
        validateService.userIdAndUserIdSameCheck(id, userId);
    }

}
