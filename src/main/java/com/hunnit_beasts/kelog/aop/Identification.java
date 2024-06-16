package com.hunnit_beasts.kelog.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 본인인증을 위한 어노테이션 입니다.
 * 컨트롤러 메서드에 달아 사용하면 됩니다.
 * 첫번째 매개변수는 token 두번째 매개변수는 @(객체)Id 이런 방식으로 사용합니다
 * ex) userId, postId
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Identification {

}
