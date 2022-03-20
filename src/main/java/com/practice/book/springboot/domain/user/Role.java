package com.practice.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 스프링 시큐리티에서는 권한 코드에 항상 'ROLE_' 이 앞에 있어야하기 때문에 코드별 키 값을 아래와 같이 지정해주었다.
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key; // final로 선언을 해야 final로 선언된 필드들을 기본 생성자로 생성해주어 DI(의존주입)을 자동으로 해준다. (별도 코드X)
    private final String title;

}
