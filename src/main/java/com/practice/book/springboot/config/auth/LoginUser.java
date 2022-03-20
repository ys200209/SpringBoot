package com.practice.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 해당 어노테이션(LoginUser)이 생성될 수 있는 위치를 지정함.
// (PARAMETER는 메서드 파라미터로 선언된 객체에서만 사용할 수 있음)
@Retention(RetentionPolicy.RUNTIME) // ?
public @interface LoginUser { // LoginUser 클래스가 아닌 어노테이션으로 생성함
    // LoginUser라는 이름을 가진 어노테이션을 생성함을 의미함. (@LoginUser)
}
