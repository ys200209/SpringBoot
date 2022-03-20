package com.practice.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User의 CRUD를 책임질 UserRepository 인터페이스
public interface UserRepository extends JpaRepository<User, Long> { // JpaRepository<'Entity 클래스', '기본키 타입'>

    // findByEmail(String email) : 소셜 로그인으로 반환되는 값 중 email을 통해 존재하는 사용자인지 처음 가입한 사용자인지 판단하기 위한 메서드.
    Optional<User> findByEmail(String email);

}
