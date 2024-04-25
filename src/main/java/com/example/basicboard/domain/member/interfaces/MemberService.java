package com.example.basicboard.domain.member.interfaces;

import com.example.basicboard.domain.member.dto.MemberResponseDTO;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.global.message.Message;
import com.example.basicboard.domain.member.dto.MemberRequestDTO;
import com.example.basicboard.domain.member.dto.SignInRequestDTO;
import com.example.basicboard.domain.member.dto.SignUpRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {

    ResponseEntity<Message<List<MemberResponseDTO>>> readAll();

    ResponseEntity<Message<MemberResponseDTO>> readById(Long id);

    ResponseEntity<Message<MemberResponseDTO>> signUp(SignUpRequestDTO requestDTO);

    ResponseEntity<Message<MemberResponseDTO>> signIn(SignInRequestDTO requestDTO);

    ResponseEntity<Message<MemberResponseDTO>> userInfoChange(Long id, MemberRequestDTO requestDTO);

    ResponseEntity<Message<String>> signOut(SignInRequestDTO requestDTO);

    ResponseEntity<Message<String>> withdrawn(Long id, String password);
}
