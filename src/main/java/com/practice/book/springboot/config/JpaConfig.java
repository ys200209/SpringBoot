package com.practice.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA 활성화를 Application.class에서 하는 것이 아닌, 별도로 main 환경에서만 구동되도록 분리함.
// test 환경에 존재하는 @WebMvcTest는 일반적인 @Configuration은 스캔하지 않는다.
public class JpaConfig { }