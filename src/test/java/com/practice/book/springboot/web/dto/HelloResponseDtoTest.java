package com.practice.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        // assertThat : 테스트 검증 라이브러리 메서드. 검증하고 싶은 대상을 메소드 인자로 받아옴
        // isEqualTo : assertThat에 있는 값과 isEqualTo의 값을 비교하여 같을 때만 성공함
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}
