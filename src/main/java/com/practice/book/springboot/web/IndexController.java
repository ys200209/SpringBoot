package com.practice.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index"; // 해당 문자열 앞의 경로로는 'src/main/resource/template/' 이 붙으며 확장자로는 '.mustache'가 붙어서 결과적으로
        // 'src/main/resource/template/index.mustache' 경로를 리턴시켜 이를 View Resolver가 처리하게 된다.
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save"; // posts-save.mustache 파일을 호출한다는 의미이다.
    }




}
