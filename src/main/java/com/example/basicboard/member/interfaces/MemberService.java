package com.example.basicboard.member.interfaces;

import com.example.basicboard.common.util.Message;
import com.example.basicboard.member.dto.MemberRequestDTO;
import com.example.basicboard.member.dto.SignUpRequestDTO;
import com.example.basicboard.member.entity.Member;
import org.springframework.http.ResponseEntity;

public interface MemberService {


    ResponseEntity<Message> readAll();
    ResponseEntity<Message> readById(Long id );
    ResponseEntity<Message> signUp(SignUpRequestDTO requestDTO);
    ResponseEntity<Message> signIn(MemberRequestDTO requestDTO);
}
