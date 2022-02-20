package com.practice.book.springboot.web.dto;

import com.practice.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto { // 내가 이해한 바로는 Dto 객체는 스프링에서의 커맨드객체와 동일한 기능을 수행(값들을 포장)함.
    private String title;
    private String content;
    private String author;
    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() { // !! RequestDto 타입의 객체를 -> to Entity(Posts) 로 타입 변환을 수행함
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
