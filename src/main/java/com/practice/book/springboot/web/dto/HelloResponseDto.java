package com.practice.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 선언된 모든 필드의 get 메소드를 생성함
@RequiredArgsConstructor // final 필드를 모두 포함하는 생성자를 생성함 (final이 없다면 포함되지 않음)
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
