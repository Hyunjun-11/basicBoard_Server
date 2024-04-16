package com.example.basicboard.domain.board.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Date date;
    private int hits;


}
