package com.practice.book.springboot.config;

import com.practice.book.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // index.js 파일 인식
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/","classpath:/image/")
                .setCachePeriod(0);
    }

    // LoginUserArgsResolver는 HandlerMethodArgumentResolver 인터페이스를 상속받고 있다.
    // 아래와 같이 HandlerMethodArgumentResolver는 '항상' WebMveConfigurer의 addArgumentResolvers() 메서드를 통해 추가해야 한다.
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolverList) {
        argumentResolverList.add(loginUserArgumentResolver);
    }
}