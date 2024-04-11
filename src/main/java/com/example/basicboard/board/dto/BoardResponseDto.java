package com.example.basicboard.board.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {


    private Long id;
    private String title;
    private String content;
    private Date date;

}
