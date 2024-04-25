package com.example.basicboard.domain.board.controller;


import com.example.basicboard.domain.board.dto.BoardRequestDTO;
import com.example.basicboard.domain.board.dto.BoardResponseDTO;
import com.example.basicboard.domain.board.service.BoardService;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.global.message.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<Message<List<BoardResponseDTO>>> readAll() {

        return boardService.readAll();

    }

    @PostMapping("/")
    public ResponseEntity<Message<BoardResponseDTO>> post(@RequestBody @Valid BoardRequestDTO boardRequestDto, Member member) {

        return boardService.boardCreate(boardRequestDto, member);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Message<BoardResponseDTO>> update(@PathVariable Long id, @RequestBody @Valid BoardRequestDTO boardRequestDto, Member member) {
        return boardService.boardUpdate(id, boardRequestDto, member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message<String>> delete(@PathVariable Long id, Member member) {
        return boardService.boardDelete(id, member);
    }


}
