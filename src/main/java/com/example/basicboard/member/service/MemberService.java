package com.example.basicboard.member.service;


import com.example.basicboard.common.util.Message;
import com.example.basicboard.common.util.StatusEnum;
import com.example.basicboard.member.dto.MemberResponseDTO;
import com.example.basicboard.member.entity.Member;
import com.example.basicboard.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;
    //멤버 전체조회
    public ResponseEntity<Message> readAll (){

        List<Member> members=memberRepository.findAll();
        Message message = Message.setSuccess(StatusEnum.OK,"멤버 전체 조회 성공");

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //멤버 단일조회
    public ResponseEntity<Message> readById (Long id ){

        Member member = findById(id);

        MemberResponseDTO responseDTO = new MemberResponseDTO(member);

        Message message = Message.setSuccess(StatusEnum.OK, " 멤버 단일 조회 성공");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //회원정보 검색 메서드
    private Member findById (Long id){
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));
        return member;

    }



}
