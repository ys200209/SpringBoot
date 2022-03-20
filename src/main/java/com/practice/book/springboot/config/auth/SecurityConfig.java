package com.practice.book.springboot.config.auth;

import com.practice.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// config.auth 디렉토리 : 스프링 시큐리티에 관련된 클래스들이 위치함
@RequiredArgsConstructor // final 필드들에 대해서 자동으로 의존성주입(DI)을 별도 코드없이 해주는 어노테이션
@EnableWebSecurity // Spring Security 설정들을 활성화(ON) 시켜준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 스프링 시큐리티 설정 클래스를 상속받아옴.

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
                .and()
                .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점. 해당 메서드가 선언되어야만 antMatchers 옵션 사용이 가능함.
                // antMatchers : 권한 관리 대상을 지정함.
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                // "/" 등의 주소들은 permitAll() 메서드를 통해 모두에게 열람 권한을 주었음.
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 설정한 것이다.
                .anyRequest().authenticated() // authenticated()을 추가하여 그 외 설정하지 않은 URL들은 모두 인증된 사용자들에게만 허용함.
                .and()
                .logout()
                    .logoutSuccessUrl("/") // 로그아웃 기능에 대한 각 설정의 진입점. 로그아웃 성공 시 "/" 주소로 이동함
                .and()
                .oauth2Login() // OAuth 2 로그인 기능에 대한 각 설정의 진입점.
                .userInfoEndpoint() // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당함.
                .userService(customOAuth2UserService);
                // 소셜 로그인 성공 이후 처리를 진행할 UserService 인터페이스의 구현체(customOAuth2UserService)를 등록함.
                // 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음.


    }

}
