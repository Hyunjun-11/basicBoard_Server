package com.example.basicboard.domain.board.service;


import com.example.basicboard.domain.board.Entity.Board;
import com.example.basicboard.domain.board.dto.BoardRequestDTO;
import com.example.basicboard.domain.board.dto.BoardResponseDTO;
import com.example.basicboard.domain.board.repository.BoardRepository;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.global.message.Message;
import com.example.basicboard.global.message.StatusEnum;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    //게시글 전체조회

    @Transactional(readOnly = true)
    public ResponseEntity<Message<List<BoardResponseDTO>>> readAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDTO> boardResponseDTO = new ArrayList<>();
        for (Board board : boards) {
            boardResponseDTO.add(new BoardResponseDTO(board));
        }
        Message<List<BoardResponseDTO>> message = Message.setSuccess(StatusEnum.OK, "게시글 전체조회", boardResponseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //게시글작성
    @Transactional
    public ResponseEntity<Message<BoardResponseDTO>> boardCreate(BoardRequestDTO requestDto, Member member) {

        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .date(requestDto.getDate())
                .member(member)
                .build();
        boardRepository.save(board);
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(board);

        Message<BoardResponseDTO> message = Message.setSuccess(StatusEnum.OK, "게시글 작성성공", boardResponseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //게시글 수정
    @Transactional
    public ResponseEntity<Message<BoardResponseDTO>> boardUpdate(Long id, BoardRequestDTO requestDto, Member member) {
        validateBoard(requestDto);

        Board board = findBoardById(id);


        if (!member.getId().equals(board.getMember().getId())) {
            System.out.println("같지않음");
            return null;
        }

        updateBoardFields(board, requestDto);

        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(board);

        boardRepository.save(board);
        Message<BoardResponseDTO> message = Message.setSuccess(StatusEnum.OK, "게시글 수정성공", boardResponseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<Message<String>> boardDelete(Long boardId, Member member) {
        Board board = findBoardById(boardId);


        if (!member.getId().equals(board.getMember().getId())) {
            System.out.println("같지않음");
            return null;
        }
        boardRepository.delete(board);

        Message<String> message = Message.setSuccess(StatusEnum.OK, "게시글 삭제성공", "게시글 삭제성공");
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    private void validateBoard(BoardRequestDTO requestDto) {
        System.out.println("게시글 검증단계");

        // 유효성 검사 로직 추가
    }

    private Board findBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
    }

    private void updateBoardFields(Board board, BoardRequestDTO requestDto) {
        if (requestDto.getTitle() != null) {
            board.setTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null) {
            board.setContent(requestDto.getContent());
        }
        if (requestDto.getDate() != null) {
            board.setDate(requestDto.getDate());
        }
        if (requestDto.getAuthor() != null) {
            board.setAuthor(requestDto.getAuthor());
        }
        if (requestDto.getHits() != null) {
            board.setHits(requestDto.getHits());
        }
    }


}
