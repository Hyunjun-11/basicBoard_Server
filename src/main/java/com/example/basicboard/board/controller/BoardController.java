package com.example.basicboard.board.controller;


import com.example.basicboard.board.dto.BoardRequestDto;
import com.example.basicboard.board.dto.BoardResponseDto;
import com.example.basicboard.board.service.BoardService;
import com.example.basicboard.common.util.Message;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<Message> readAll(){

        return boardService.readAll();

    }

    @PostMapping("/")
    public ResponseEntity<Message> post(@RequestBody BoardRequestDto boardRequestDto){

        return boardService.BoardCreate(boardRequestDto);

    }







}
