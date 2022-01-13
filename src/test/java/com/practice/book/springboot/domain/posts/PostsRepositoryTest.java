package com.practice.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest { // deleteAll(), save(), findAll()과 같이 리포지토리로부터 JPA CRUD 메소드를 테스트함.

    @Autowired
    PostsRepository postsRepository;

    @After // JUnit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정함
    // 여러 테스트가 동시에 실행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 다음 테스트 실행 시 실패할 수 있기 때문에..
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() { // INSERT & SELECT
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // save() : 테이블 posts에 아래의 id값이 존재하면 update를, 존재하지 않는다면 insert를 수행한다.
        // 별다른 설정없이 H2 데이터베이스를 자동으로 실행함.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("LeeSeYeong@naver.com").build());

        // when
        // findAll() : posts 테이블에 존재하는 모든 데이터를 조회해오는 메소드.
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {

        // given
        LocalDateTime now = LocalDateTime.of(2022, 1, 8, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>> createDate="+posts.getCreateDate()+", modelfiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);




    }

}
