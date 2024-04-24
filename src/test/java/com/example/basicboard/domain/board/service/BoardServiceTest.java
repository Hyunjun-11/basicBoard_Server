package com.example.basicboard.domain.board.service;


import com.example.basicboard.domain.board.Entity.Board;
import com.example.basicboard.domain.board.dto.BoardRequestDTO;
import com.example.basicboard.domain.board.dto.BoardResponseDTO;
import com.example.basicboard.domain.board.repository.BoardRepository;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.domain.member.interfaces.MemberService;
import com.example.basicboard.domain.member.repository.MemberRepository;
import com.example.basicboard.global.message.Message;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    MemberRepository memberRepository;


    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .memberId("멤버아이디")
                .memberName("멤버이름")
                .password("{noop}1111")
                .gender(Member.Gender.MALE)
                .memberPhone("멤버전화번호")
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        boardRepository.deleteAll();
    }


    private BoardRequestDTO createBoardDTO() {
        BoardRequestDTO boardDTO = new BoardRequestDTO();
        boardDTO.setTitle("제목");
        boardDTO.setContent("내용");
        boardDTO.setAuthor("저자");
        boardDTO.setDate(LocalDateTime.now());
        boardDTO.setHits(1);
        return boardDTO;
    }


    @Order(1)
    @DisplayName("게시판 전체조회")
    @Test
    void readAll() {
        // Given (주어진 상황)
        LocalDateTime dateTime = LocalDateTime.of(2024, 4, 4, 12, 0, 0);
        List<Board> boards = new ArrayList<>();
        Board board1 = Board.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .hits(1)
                .date(dateTime)
                .member(member)
                .build();

        Board board2 = Board.builder()
                .id(2L)
                .title("제목")
                .content("내용")
                .hits(1)
                .date(dateTime)
                .member(member)
                .build();

        boards.add(board1);
        boards.add(board2);
        boardRepository.saveAll(boards);
        //when
        ResponseEntity<Message<List<BoardResponseDTO>>> responseEntity = boardService.readAll();
        // Then (결과 확인)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
        assertEquals("게시글 전체조회", responseEntity.getBody().getMessage());
        Message<List<BoardResponseDTO>> message = responseEntity.getBody();
        List<BoardResponseDTO> boardList = message.getData();
        assertEquals(2, boardList.size());


    }

    @Order(2)
    @DisplayName("게시판생성 테스트")
    @Test
    void createBoard() {
        //given
        BoardRequestDTO boardDTO = createBoardDTO();
        //when
        ResponseEntity<Message<BoardResponseDTO>> responseEntity = boardService.boardCreate(boardDTO, member);
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("게시글 작성성공", responseEntity.getBody().getMessage());

    }

    @Order(3)
    @DisplayName("게시판 수정 테스트")
    @Test
    void updateBoard() {
        //given
        BoardRequestDTO boardDTO = createBoardDTO();
        boardService.boardCreate(boardDTO, member);
        ResponseEntity<Message<BoardResponseDTO>> createResponse = boardService.boardCreate(boardDTO, member);
        Long createdBoardId = Objects.requireNonNull(createResponse.getBody()).getData().getId();

        BoardRequestDTO updateBoard = new BoardRequestDTO();
        updateBoard.setTitle("수정된제목");
        updateBoard.setContent("수정된내용");
        updateBoard.setAuthor("수정된저자");
        updateBoard.setDate(LocalDateTime.now());
        updateBoard.setHits(2);
        //when
        ResponseEntity<Message<BoardResponseDTO>> responseEntity = boardService.boardUpdate(createdBoardId, updateBoard, member);
        //then
        Message<BoardResponseDTO> message = responseEntity.getBody();
        assertNotNull(message);
        assertEquals("게시글 수정성공", responseEntity.getBody().getMessage());
        //수정된 글 확인
        BoardResponseDTO updatedBoard = message.getData();
        assertNotNull(updatedBoard);

        //전내용과 비교
        assertNotEquals("제목", updatedBoard.getTitle());
        assertNotEquals("내용", updatedBoard.getContent());

        assertEquals(createdBoardId, updatedBoard.getId());
        assertEquals("수정된제목", updatedBoard.getTitle());
        assertEquals("수정된내용", updatedBoard.getContent());


    }

    @Order(4)
    @DisplayName("게시판 삭제")
    @Test
    void deleteBoard() {
        // Given
        BoardRequestDTO boardDTO = createBoardDTO();
        boardService.boardCreate(boardDTO, member);
        ResponseEntity<Message<BoardResponseDTO>> createResponse = boardService.boardCreate(boardDTO, member);
        Long createdBoardId = Objects.requireNonNull(createResponse.getBody()).getData().getId();
        // When
        ResponseEntity<Message<String>> responseEntity = boardService.boardDelete(createdBoardId, member);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
        assertEquals("게시글 삭제성공", Objects.requireNonNull(responseEntity.getBody()).getData());


        assertTrue(boardRepository.findById(createdBoardId).isEmpty());
        assertFalse(boardRepository.findById(createdBoardId).isPresent());


    }


}
