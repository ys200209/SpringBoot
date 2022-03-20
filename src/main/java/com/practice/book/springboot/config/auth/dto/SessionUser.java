package com.practice.book.springboot.config.auth.dto;

import com.practice.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
// '직렬화 기능을 가진 세션 Dto'
public class SessionUser implements Serializable { // 해당 클래스에는 '인증된 사용자 정보'만 필요함. 그 외에 정보는 필요없으니 다른 필드를 생성하지 않음
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
