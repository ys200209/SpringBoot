package com.practice.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @EnableJpaAuditing (main과 test 환경 둘을 분리해놓기 위해 여기서 선언하는 것이 아닌, config.JpaConfig에 추가해줌)
// (그렇지 않으면 test 환경에서 Jpa를 사용해야 하는데 test 환경에서는 Entity를 생성하지 않기 때문에 에러가 발생한다)
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
