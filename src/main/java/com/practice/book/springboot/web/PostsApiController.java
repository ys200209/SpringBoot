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
        System.out.println("requestDto.getContent() : " + requestDto.getContent());
        System.out.println("requestDto.getAuthor() : " + requestDto.getAuthor());
        System.out.println("requestDto.getTitle() : " + requestDto.getTitle());
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

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
        // delete는 삭제만 할뿐, 추가로 변경된 객체를 줄 필요가 없기 때문에
        // return을 void로 주지 않았더니 Error가 발생했다.
        // return 해봤자 아무 의미도 없고 리턴한다고 해서 누가 받아 사용하는 것으로
        // 보이지도 않는데 id를 리턴해야 parserError가 뜨지 않는다.
    }


}
