package com.practice.book.springboot.domain.user;

import com.practice.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 게터를 필드별로 생성해라
@NoArgsConstructor // 기본 생성자를 생성해라
@Entity // 이 클래스는 엔티티 클래스다 (DB의 테이블이 될 클래스다)
public class User extends BaseTimeEntity { // 구글의 로그인 인증정보를 사용하기 위해 사용자 정보를 담당할 도메인인 User 클래스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    // 데이터베이스를 JPA로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정함. (디폴트는 int형이기 때문에 그 값이 무슨 코드인지 알 수가 없어서 String으로 저장.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
