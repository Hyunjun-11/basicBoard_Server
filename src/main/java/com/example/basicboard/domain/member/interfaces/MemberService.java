package com.example.basicboard.domain.member.interfaces;

import com.example.basicboard.global.message.Message;
import com.example.basicboard.domain.member.dto.MemberRequestDTO;
import com.example.basicboard.domain.member.dto.SignInRequestDTO;
import com.example.basicboard.domain.member.dto.SignUpRequestDTO;
import org.springframework.http.ResponseEntity;

public interface MemberService {


    ResponseEntity<Message> readAll();

    ResponseEntity<Message> readById(Long id);

    ResponseEntity<Message> signUp(SignUpRequestDTO requestDTO);

    ResponseEntity<Message> signIn(SignInRequestDTO requestDTO);

    ResponseEntity<Message> userInfoChange(Long id, MemberRequestDTO requestDTO);

    ResponseEntity<Message> signOut(SignInRequestDTO requestDTO);

    ResponseEntity<Message> withdrawn(Long id, String password);
}
