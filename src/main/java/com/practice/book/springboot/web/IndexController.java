package com.practice.book.springboot.web;

import com.practice.book.springboot.service.posts.PostsService;
import com.practice.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor // Bean 주입을 @Autowired가 아닌 생성자로 주입받음 (가장 권장하는 방법)
@Controller
public class IndexController { // 머스테치 뷰 페이지 컨트롤러 (데이터 전송 및 저장 등의 컨트롤러는 API 컨트롤러이다.)

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) { // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장한다.
        model.addAttribute("posts", postsService.findAllDesc());
        // postsService.findAllDesc()로 가져온 결과를 posts라는 이름으로 index.mustache에 전달한다는 의미이다.

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
