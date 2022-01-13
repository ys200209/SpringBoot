package com.practice.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application { // 1
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS를 실행하는 코드.
    }
}
