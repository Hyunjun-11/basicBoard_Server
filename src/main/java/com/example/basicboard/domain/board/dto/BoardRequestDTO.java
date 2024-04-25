package com.example.basicboard.domain.board.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDTO {
    private Long id;
    @Size(max = 30, message = "제목은 30글자를 넘을 수 없습니다.")
    @NotNull(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "작성자를 입력해주세요.")
    private String author;
    @NotNull(message = "내용을 입력해주세요.")
    private String content;
    private LocalDateTime date;
    private Integer hits;


}
