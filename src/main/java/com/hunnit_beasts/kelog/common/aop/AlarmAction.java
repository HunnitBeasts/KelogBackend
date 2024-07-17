package com.hunnit_beasts.kelog.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 알람기능을 위한 어노테이션 입니다.
 * 컨트롤러 메서드에 달아 사용하면 됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AlarmAction {

}
