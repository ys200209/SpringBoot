package com.practice.book.springboot.web;

import com.practice.book.springboot.service.posts.PostsService;
import com.practice.book.springboot.web.dto.PostsResponseDto;
import com.practice.book.springboot.web.dto.PostsSaveRequestDto;
import com.practice.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // Bean 주입을 @Autowired가 아닌 생성자로 주입받음 (가장 권장하는 방법)
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts") // INSERT
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}") // UPDATE (방식이 PUT 이므로 일반적인 URL로 접근할 수 없다. 파라미터를 확인해보면 된다.)
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}") // SELECT {id} (방식이 GET 이므로 일반적인 조회이다.)
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }



}
