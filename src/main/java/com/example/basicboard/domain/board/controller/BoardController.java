package com.example.basicboard.domain.board.controller;


import com.example.basicboard.domain.board.dto.BoardRequestDTO;
import com.example.basicboard.domain.board.service.BoardService;
import com.example.basicboard.global.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<Message> readAll() {

        return boardService.readAll();

    }

    @PostMapping("/")
    public ResponseEntity<Message> post(@RequestBody BoardRequestDTO boardRequestDto) {

        return boardService.boardCreate(boardRequestDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id, @RequestBody BoardRequestDTO boardRequestDto) {
        return boardService.boardUpdate(id, boardRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable Long id) {
        return boardService.boardDelete(id);
    }


}
