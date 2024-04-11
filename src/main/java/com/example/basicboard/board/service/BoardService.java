package com.example.basicboard.board.service;


import com.example.basicboard.board.Entity.Board;
import com.example.basicboard.board.dto.BoardRequestDto;
import com.example.basicboard.board.repository.BoardRepository;
import com.example.basicboard.common.util.Message;
import com.example.basicboard.common.util.StatusEnum;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    //게시글 전체조회
    @Transactional(readOnly = true)
    public ResponseEntity<Message> readAll(){

        List<Board>  boards=boardRepository.findAll();

        Message message = Message.setSuccess(StatusEnum.OK,"게시글 전체조회", boards);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //게시글작성
    @Transactional
    public ResponseEntity<Message> BoardCreate(BoardRequestDto requestDto){

        Board board= Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .date(requestDto.getDate())
                .build();
        boardRepository.save(board);

        Message message = Message.setSuccess(StatusEnum.OK,"게시글 작성성공",board);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<Message> BoardUpdate(Long id,BoardRequestDto requestDto){

        validateBoard(requestDto);

        Board board = boardRepository.findById(id).orElseThrow(()->new EntityNotFoundException("게시글을 찾을 수 없습니다"));

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setDate(requestDto.getDate());
        boardRepository.save(board);

        Message message = Message.setSuccess(StatusEnum.OK,"게시글 수정성공",board);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    private void validateBoard(BoardRequestDto requestDto) {
        System.out.println("게시글 검증단계");

        // 유효성 검사 로직 추가
    }



}
