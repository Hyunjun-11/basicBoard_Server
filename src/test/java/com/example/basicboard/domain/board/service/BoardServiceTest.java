package com.example.basicboard.domain.board.service;


import com.example.basicboard.domain.board.Entity.Board;
import com.example.basicboard.domain.board.dto.BoardRequestDTO;
import com.example.basicboard.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;


    @Test
    void readAll() {
        Board board = new Board();
    }


    @Test
    void createBoard() {

        BoardRequestDTO boardDTO = new BoardRequestDTO();
        boardDTO.setId(1L);
        boardDTO.setTitle("제목");
        boardDTO.setContent("내용");
        boardDTO.setAuthor("저자");
        boardDTO.setDate(LocalDateTime.now());
        boardDTO.setHits(1);


        boardService.boardCreate(boardDTO);


    }

}
