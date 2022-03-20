package com.practice.book.springboot.config.auth;

import com.practice.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor // final 키워드가 붙어있는 파라미터에 의존성을 주입해주는 어노테이션
@Component
public class LoginUserArgumentResolver implements
        HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 컨트롤러 메서드의 특정 파라미터를 지원(support) 하는지 판단한다.

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터에 @LoginUser 어노테이션이 붙어있는지 여부를 판단한다.

        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        // 파라미터 클래스 타입이 SessionUser.class 가 맞는지 여부를 판단한다.

        return isLoginUserAnnotation && isUserClass; // 둘 다 참이라면 true를 리턴함
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체를 생성하는 메서드이며, 여기서는 세션에서 객체를 가져온다.

        return httpSession.getAttribute("user");
    }
}
