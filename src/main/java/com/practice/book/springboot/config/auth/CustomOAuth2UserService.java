package com.practice.book.springboot.config.auth;

import com.practice.book.springboot.config.auth.dto.OAuthAttributes;
import com.practice.book.springboot.config.auth.dto.SessionUser;
import com.practice.book.springboot.domain.user.User;
import com.practice.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor // final로 선언한 필드를 별도의 코드 없이 자동으로 의존성주입(DI)을 수행해주는 어노테이션
@Service
// 구글 로그인 이후 가져온 사용자의 정보(email, name, picture 등)을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원하기 위한 서비스 클래스
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 구글, 네이버 등 여러 서비스중 어느 서비스로 로그인하였는지 구분함.
        String registrationId = userRequest.
                getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키(Primary Key)가 되는 필드값이 되는 변수임.
        // 구글의 경우 기본 코드로 "sub"를 지원하지만 네이버, 카카오 등은 기본 지원하지 않는다.
        // 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용됨.
        String userNameAttributeName = userRequest.
                getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        System.out.println("1 userNameAttributeName : " + userNameAttributeName);

        // OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.
        // 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용함.
        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName,
                        oAuth2User.getAttributes());

        // SessionUser : 로그인에 성공한 후 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // Why User 클래스를 사용하지 않고 새로 만들어서 쓰는지 ?
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        System.out.println("2 userNameAttributeName : " + attributes.getNameAttributeKey());
        return new DefaultOAuth2User(
                Collections.singleton(new
                        SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 때를 대비해 update 기능도 같이 구현함.
    // name 및 picture가 변경되면 User 엔티티에도 반영이 되도록.
    private User saveOrUpdate(OAuthAttributes attributes) {
        System.out.println("(saveOrUpdate) attributes.getEmail() = " + attributes.getEmail());
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.
                        getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
