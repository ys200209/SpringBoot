package com.practice.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.book.springboot.domain.posts.Posts;
import com.practice.book.springboot.domain.posts.PostsRepository;
import com.practice.book.springboot.web.dto.PostsSaveRequestDto;
import com.practice.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    // 웹에만 집중하는 @WebMvcTest 애노테이션이 아닌 JPA 기능도 동시에 테스트 하고자 할때 사용하는 객체 (TestRestTemplate)

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc; // @WithMockUser 어노테이션이 MockMvc에서만 동작하기 때문에 이를 위해 생성해줌.

    @Before
    public void setup() { // 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성함
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER") // 인증된 가짜 사용자를 만들어서 사용함. (roles 권한 추가 가능)
    // 위의 어노테이션을 통해 ROLE_USER 권한을 가진 사용자가 실제로 API를 요청하는 것과 동일한 효과를 가짐.
    public void 게시글_등록하기() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when MockMvc 로 대체됨 P.217
        // ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // 이렇게 코드를 보내는 순간, Controller에 의해 해당 URL은 캐치되고
        // 그와 동시에 해당 URL로 전송된 RequestDto 객체는 Service에 의해서 Posts 객체로 변환되어 Entity에 저장된다.

        // mvc.perform : 생성된 MockMvc를 통해 API를 테스트 한다.
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        // then
        // 아래 코드는 위의 MockMvc 의 perform 메서드로 대체됨
        /*
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isGreaterThan(0L);
        */

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void 게시글_수정하기() throws Exception { // UPDATE
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String updateTitle = "변경된 테스트 게시글";
        String updateContent = "변경된 테스트 본문";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;



        // when
        /*
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEntity, Long.class);
        */
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        /*
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isGreaterThan(0L);
        */

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);


    }


}
