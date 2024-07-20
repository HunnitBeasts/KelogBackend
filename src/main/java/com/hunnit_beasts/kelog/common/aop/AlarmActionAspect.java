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
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class AlarmActionAspect {

    private final AuthenticatedService authenticatedService;
    private final AlarmService alarmService;

    @After("@annotation(com.hunnit_beasts.kelog.common.aop.AlarmAction)")
    public void alarm(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AlarmAction alarmAction = method.getAnnotation(AlarmAction.class);
        AlarmType type = alarmAction.value();

        Map<String, Object> parameters = extractParameters(joinPoint);
        Long id = (Long) parameters.remove("id");
        Object dto = parameters.remove("dto");

        switch (type){
            case LIKE:
                alarmService.newLikeAlarm(id,(PostLikeRequestDTO) dto);
                break;
            case FOLLOW:
                alarmService.newFollowAlarm(id,(FollowIngRequestDTO) dto);
                break;
            case COMMENT:
                alarmService.newCommentAlarm(id,(CommentCreateRequestDTO) dto);
                break;
            case SUBSCRIBE:
                alarmService.newPostAlarm(id);
                break;
            default: throw new ExpectException(ErrorCode.NO_ALARM_TYPE_ERROR);
        }
    }

    private Map<String, Object> extractParameters(JoinPoint joinPoint) {
        Map<String, Object> parameters = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0)
            throw new ExpectException(ErrorCode.NO_PARAMETER_ERROR);

        for (int i = 0; i < parameterNames.length; i++) {
            if ("authentication".equals(parameterNames[i])) {
                Object id = authenticatedService.getId((Authentication) args[i]);
                parameters.put("id", id);
            }
            else if ("dto".equals(parameterNames[i]))
                parameters.put(parameterNames[i], args[i]);
        }
        return parameters;
    }
}
