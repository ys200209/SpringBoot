package com.practice.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // SpringRunner라는 스프링 실행자를 이용해 테스트를 진행함
@WebMvcTest(controllers = HelloController.class) // Web에 집중할 수 있는 애노테이션
public class HelloControllerTest {

    @Autowired // 빈 주입
    private MockMvc mvc; // 웹 API를 테스트하기 위한 변수 선언 (GET, POST등의 API 수행)

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // '/hello' 주소로 get 요청
                .andExpect(status().isOk()) // 요청에 대한 응답을 검증 (200 코드인지 확인)
                .andExpect(content().string(hello)); // 응답 본문에 대한 내용 자체를 검증 ("hello" 문자열이 맞는지 검증)
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // API 테스트할 때 사용될 요청 파라미터를 설정
                        .param("amount", String.valueOf(amount)) // (단, String 값만 허용됨. 숫자/날짜 모두 변환.)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // JSON 응답값을 필드별로 검증하는 메서드
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}