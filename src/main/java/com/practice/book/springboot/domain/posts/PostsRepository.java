package com.practice.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // DB 접근자 (DAO라고 부르던걸 JPA는 Repository라고 함)
    // 단순히 인터페이스로 생성하며, JpaRepository<'Entity 클래스', '기본키 타입'> 을 상속하면 CRUD 메소드가 자동으로 생성됨
    // 주의 사항으로는 Entity 클래스와 Repository는 반드시 함께 위치해야 한다는 점이다 (같은 패키지에).

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // JPA에서 제공하지 않는 메소드는 이와같이 쿼리로 작성해도 가능하다.
    List<Posts> findAllDesc(); // 실제로 위와 같은 쿼리는 JPA에서 제공하는 기본 메소드만으로도 해결 가능하다. (다만 가독성은 위의 쿼리가 좋음)

}
