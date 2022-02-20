package com.practice.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@SpringBootApplication
public class Application extends SpringBootServletInitializer { // 1
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS를 실행하는 코드.
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer { // resources 폴더의 js에 접근이 안되어서 적었더니 됨
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/"/*,"classpath:/image/"*/)
                    .setCachePeriod(0);
        }
    }

}
