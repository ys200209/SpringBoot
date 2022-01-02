package com.practice.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // DB 접근자 (DAO라고 부르던걸 JPA는 Repository라고 함)
    // 단순히 인터페이스로 생성하며, JpaRepository<'Entity 클래스', '기본키 타입'> 을 상속하면 CRUD 메소드가 자동으로 생성됨
    // 주의 사항으로는 Entity 클래스와 Repository는 반드시 함께 위치해야 한다는 점이다.

}
