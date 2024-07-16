package com.hunnit_beasts.kelog.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AlarmAspect{

    @After("@annotation(com.hunnit_beasts.kelog.common.aop.Alarm)")
    public void alarm(JoinPoint joinPoint){

    }
}
