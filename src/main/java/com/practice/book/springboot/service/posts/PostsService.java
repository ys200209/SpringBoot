package com.practice.book.springboot.service.posts;

import com.practice.book.springboot.domain.posts.Posts;
import com.practice.book.springboot.domain.posts.PostsRepository;
import com.practice.book.springboot.web.dto.PostsListResponseDto;
import com.practice.book.springboot.web.dto.PostsResponseDto;
import com.practice.book.springboot.web.dto.PostsSaveRequestDto;
import com.practice.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 필드에 대해서 의존성 주입(DI)을 @Autowired가 아닌 생성자로 '임의의 코드 없이' 자동으로 설정
@Service
public class PostsService { // Repository를 상속받아 DB로의 CRUD를 직접적으로 수행하는 서비스 클래스

    private final PostsRepository postsRepository; // final로 선언을 해야 DI를 자동으로 주입 받을 수 있다. ( @RequiredArgsConstrictor )

    @Transactional // INSERT
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional // UPDATE
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) { // SELECT
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 읽기(조회) 전용으로 등록, 수정, 삭제는 할 수 없지만 조회 속도가 개선된다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream() // 쿼리 결과 스트림을 map을 통해
                .map(PostsListResponseDto::new) // PostsListResponseDto로 변환하고
                .collect(Collectors.toList()); // List로 반환하는 메서드
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts); // Repository에 존재하는 삭제 메서드를 이용.
    }

}
