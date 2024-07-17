package com.hunnit_beasts.kelog.common.aop;

import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class AlarmActionAspect {

    private final AuthenticatedService authenticatedService;
    private final AlarmService alarmService;

    @AfterReturning(value = "@annotation(com.hunnit_beasts.kelog.common.aop.AlarmAction) && args(type, ..)", returning = "returnObj", argNames = "joinPoint,type,returnObj")
    public void alarm(JoinPoint joinPoint, AlarmType type,Object returnObj){

    }

    private Map<String, Long> extractParameters(JoinPoint joinPoint) {
        Map<String, Long> parameters = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0)
            throw new ExpectException(ErrorCode.NO_PARAMETER_ERROR);

        for (int i = 0; i < parameterNames.length; i++) {
            if ("authentication".equals(parameterNames[i])) {
                Long id = authenticatedService.getId((Authentication) args[i]);
                parameters.put("sender", id);
            } else if ("dto".equals(parameterNames[i]))
                parameters.put(parameterNames[i], (Long) args[i]);
            else if (parameterNames[i].equals("params"))
                addParameter(parameters, args[i]);
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

}
