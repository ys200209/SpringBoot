package com.practice.book.springboot.web;

import com.practice.book.springboot.config.auth.LoginUser;
import com.practice.book.springboot.config.auth.dto.SessionUser;
import com.practice.book.springboot.service.posts.PostsService;
import com.practice.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor // Bean 주입을 @Autowired가 아닌 생성자로 주입받음 (가장 권장하는 방법)
@Controller
public class IndexController { // 머스테치 뷰 페이지 컨트롤러 (데이터 전송 및 저장 등의 컨트롤러는 API 컨트롤러이다.)

    private final PostsService postsService;
    private final HttpSession httpSession; // 의존성주입(DI)

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장한다.

        model.addAttribute("posts", postsService.findAllDesc());
        // postsService.findAllDesc()로 가져온 결과를 posts라는 이름으로 index.mustache에 전달한다는 의미이다.

        // CustomOAuth2UserService 클래스에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구현하였음.
        // 현재 메서드인 index() 메서드 외에 다른 컨트롤러 or 메서드에서 세션값이 필요할 때마다 아래 코드를 적는 것은 코드 중복 문제를 야기함.
        // 따라서 아래 세션값 받아오는 부분을 메서드 인자로 세션값을 바로 받을 수 있는 방식으로 구현할 것임. (@LoginUser 클래스 어노테이션)
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // 해당 index() 메서드의 파라미터로 세션을 넘겨받는 식으로 구현해주었으며, 앞으로도 다른 부분에서 세션이 필요하면 이와 같이 하면 된다.

        if (user != null) { // 세션 값이 null이 아니라면
            model.addAttribute("userName", user.getName()); // 사용자 이름도 전달해라.
        }

        return "index"; // 해당 문자열 앞의 경로로는 'src/main/resource/template/' 이 붙으며 확장자로는 '.mustache'가 붙어서 결과적으로
        // 'src/main/resource/template/index.mustache' 경로를 리턴시켜 이를 View Resolver가 처리하게 된다.
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save"; // posts-save.mustache 파일을 호출한다는 의미이다.
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }




}
