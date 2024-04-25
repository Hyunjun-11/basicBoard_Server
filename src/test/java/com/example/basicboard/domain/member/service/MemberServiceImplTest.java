package com.example.basicboard.domain.member.service;

import com.example.basicboard.domain.member.dto.MemberResponseDTO;
import com.example.basicboard.domain.member.dto.SignInRequestDTO;
import com.example.basicboard.domain.member.dto.SignUpRequestDTO;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.domain.member.interfaces.MemberService;
import com.example.basicboard.domain.member.repository.MemberRepository;
import com.example.basicboard.global.message.Message;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    Member beforeMember1;
    Member beforeMember2;

    List<Member> beforeMemberList;

    @BeforeEach
    void setUp() {
        beforeMemberList = new ArrayList<>();
        beforeMember1 = Member.builder()
                .id(1L)
                .memberId("멤버아이디")
                .memberName("멤버이름")
                .password("{noop}1111")
                .gender(Member.Gender.MALE)
                .memberPhone("멤버전화번호")
                .build();
        beforeMemberList.add(beforeMember1);


        beforeMember2 = Member.builder()
                .id(2L)
                .memberId("멤버아이디2")
                .memberName("멤버이름")
                .password("{noop}1111")
                .gender(Member.Gender.MALE)
                .memberPhone("멤버전화번호")
                .build();
        beforeMemberList.add(beforeMember2);
        memberRepository.saveAll(beforeMemberList);
    }


    private SignUpRequestDTO CreateSignUpRequestDTO() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMemberId("test11");
        signUpRequestDTO.setPassword("test11");
        signUpRequestDTO.setMemberName("닉네임뭘로해");
        signUpRequestDTO.setMemberPhone("010-1234-1234");
        signUpRequestDTO.setGender(Member.Gender.MALE);

        return signUpRequestDTO;

    }

    @Order(1)
    @DisplayName("회원 전체조회")
    @Test
    void readAll() {

        //given
        List<Member> members = new ArrayList<>();

        Member member1 = Member.builder()
                .memberId("멤버아이디3")
                .memberName("멤버이름1")
                .password("{noop}1111")
                .gender(Member.Gender.MALE)
                .memberPhone("멤버전화번호")
                .build();
        members.add(member1);

        Member member2 = Member.builder()
                .memberId("멤버아이디4")
                .memberName("멤버이름2")
                .password("{noop}1111")
                .gender(Member.Gender.MALE)
                .memberPhone("멤버전화번호")
                .build();
        members.add(member2);

        memberRepository.saveAll(members);

        //when
        ResponseEntity<Message<List<MemberResponseDTO>>> responseEntity = memberService.readAll();


        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
        assertEquals("멤버 전체 조회 성공", responseEntity.getBody().getMessage());

    }

    @Order(2)
    @DisplayName("회원 단일조회")
    @Test
    void readById() {
        //Given
        //When
        ResponseEntity<Message<MemberResponseDTO>> responseEntity = memberService.readById(1L);
        //Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
        MemberResponseDTO memberResponseDTO = responseEntity.getBody().getData();
        assertEquals(1L, memberResponseDTO.getId());
        assertEquals("멤버이름", memberResponseDTO.getMemberName());

    }

    @Order(3)
    @DisplayName("중복아이디 회원가입")
    @Test
    void duplicateIdSignUp() {
        //given
        SignUpRequestDTO requestDTO = CreateSignUpRequestDTO();
        requestDTO.setMemberId("멤버아이디");
        //when
        ResponseEntity<Message<MemberResponseDTO>> responseEntity = memberService.signUp(requestDTO);
        //then
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("이미 존재하는 회원입니다.", Objects.requireNonNull(responseEntity.getBody()).getMessage());


    }

    @Order(4)
    @DisplayName("회원가입")
    @Test
    void signUp() {
        //given
        SignUpRequestDTO requestDTO = CreateSignUpRequestDTO();
        //when
        ResponseEntity<Message<MemberResponseDTO>> responseEntity = memberService.signUp(requestDTO);
        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("회원가입 성공", Objects.requireNonNull(responseEntity.getBody()).getMessage());

        MemberResponseDTO responseDTO = responseEntity.getBody().getData();
//        assertEquals(3L, responseDTO.getId());
        assertEquals("test11", responseDTO.getMemberId());
        assertEquals("닉네임뭘로해", responseDTO.getMemberName());
    }

    @Order(5)
    @DisplayName("로그인")
    @Test
    void signIn() {
        //given
        SignUpRequestDTO signUpRequestDTO = CreateSignUpRequestDTO();
        signUpRequestDTO.setMemberId("logintest");
        ResponseEntity<Message<MemberResponseDTO>> signUpresponseEntity = memberService.signUp(signUpRequestDTO);
        MemberResponseDTO signUpResponseDTO = Objects.requireNonNull(signUpresponseEntity.getBody()).getData();

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setMemberId("logintest");
        signInRequestDTO.setPassword("test11");
        //when
        ResponseEntity<Message<MemberResponseDTO>> signInResponseEntity = memberService.signIn(signInRequestDTO);
        //then

        assertEquals(HttpStatus.OK, signInResponseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(signInResponseEntity.getBody()).getData());
        assertEquals("로그인 성공", signInResponseEntity.getBody().getMessage());


        MemberResponseDTO signInResponseDTO = signInResponseEntity.getBody().getData();
        assertEquals(signUpResponseDTO.getId(), signInResponseDTO.getId());
        assertEquals(signUpResponseDTO.getMemberId(), signInResponseDTO.getMemberId());
    }

    @Order(6)
    @DisplayName("다른 비밀번호로그인")
    @Test
    void failSignInOfPassword() {
        //given
        SignUpRequestDTO signUpRequestDTO = CreateSignUpRequestDTO();
        memberService.signUp(signUpRequestDTO);


        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setMemberId("test11");
        signInRequestDTO.setPassword("failpassword");
        //when
        ResponseEntity<Message<MemberResponseDTO>> signInResponseEntity = memberService.signIn(signInRequestDTO);
        //then

        assertEquals(HttpStatus.BAD_REQUEST, signInResponseEntity.getStatusCode());
        assertNotEquals(HttpStatus.OK, signInResponseEntity.getStatusCode());
        assertNull(Objects.requireNonNull(signInResponseEntity.getBody()).getData());
        assertEquals("회원 정보가 올바르지 않습니다.", signInResponseEntity.getBody().getMessage());

    }


    @DisplayName("회원정보변경")
    @Test
    void userInfoChange() {
        //given
        SignUpRequestDTO signUpRequestDTO = CreateSignUpRequestDTO();
        memberService.signUp(signUpRequestDTO);


    }


    @DisplayName("로그아웃")
    @Test
    void signOut() {
    }


    @DisplayName("회원탈퇴")
    @Test
    void withdrawn() {
    }
}