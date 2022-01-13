package com.practice.book.springboot.domain.posts;

import com.practice.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 필드들에 대한 게터를 생성해주는 애노테이션
@NoArgsConstructor // 필드값이 없는 (NoArgs) 기본 생성자를 생성해주는 애노테이션
@Entity // 테이블과 링크될 클래스임을 나타내는 애노테이션
public class Posts extends BaseTimeEntity {
// Entity 클래스는 절대로 세터를 생성하지 말아야 한다.(p.92) 값 변경이 필요하다면 명확히 목적과 의도를 나타낼 수 있는 메소드를 추가함.

    @Id // 해당 테이블의 기본키를 나타내는 애노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키에 대한 규칙을 나타냄 (파라미터는 auto_increment 라는 의미.)
    private Long id; // 웬만하면 기본키는 Long타입의 Auto_increment를 추천한다.

    // 테이블의 칼럼을 나타냄. (굳이 안해도 해당 클래스의 필드는 모두 칼럼이 됨)
    // 그럼에도 사용하는 이유는 기본 칼럼 외에 추가로 변경이 필요한 옵션이 있을 경우 사용함.
    @Column(length = 500, nullable = false)
    private String title;

    // 문자열의 경우 varchar(255)가 기본값이지만 길이를 500으로 늘리고싶거나(title) 타입을 TEXT로 변경하고 싶은경우(content) 처럼.

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성(생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함한다)(?)
    // 굳이 생성자를 쓰지 않는 이유는 생성자의 경우 지금 당장 채워야 할 필드가 무엇인지 명확히 지정할 수가 없을 수 있다 (p.93)
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
