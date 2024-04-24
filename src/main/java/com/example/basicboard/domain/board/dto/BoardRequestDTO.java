package com.example.basicboard.domain.board.dto;


import com.example.basicboard.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime date;
    private Integer hits;


}
