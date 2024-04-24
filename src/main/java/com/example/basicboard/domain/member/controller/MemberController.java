package com.example.basicboard.domain.member.controller;


import com.example.basicboard.domain.member.dto.MemberResponseDTO;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.global.message.Message;
import com.example.basicboard.domain.member.dto.SignInRequestDTO;
import com.example.basicboard.domain.member.service.MemberServiceImpl;
import com.example.basicboard.domain.member.dto.MemberRequestDTO;
import com.example.basicboard.domain.member.dto.SignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;


    //회원전체조회
    @GetMapping("/")
    public ResponseEntity<Message<List<MemberResponseDTO>>> readAll() {
        return memberService.readAll();
    }

    //회원 단일조회
    @GetMapping("/{id}")
    public ResponseEntity<Message<MemberResponseDTO>> readById(@PathVariable Long id) {
        return memberService.readById(id);

    }

    //회원가입
    @PostMapping("/singnUp")
    public ResponseEntity<Message<MemberResponseDTO>> singUp(@RequestBody SignUpRequestDTO requestDTO) {
        return memberService.signUp(requestDTO);
    }

    //회원정보 변경
    @PutMapping("/userInfo/{id}")
    public ResponseEntity<Message<MemberResponseDTO>> userInfoChange(@PathVariable Long id, @RequestBody MemberRequestDTO requestDTO) {
        return memberService.userInfoChange(id, requestDTO);
    }

    //로그인
    @PostMapping("/signIn")
    public ResponseEntity<Message<MemberResponseDTO>> signIn(@RequestBody SignInRequestDTO requestDTO) {
        return memberService.signIn(requestDTO);
    }

    //로그아웃
    @PostMapping("/signOut")
    public ResponseEntity<Message<String>> signOut(@RequestBody SignInRequestDTO requestDTO) {
        return memberService.signOut(requestDTO);

    }

    //회원탈퇴(soft-delete)
    @PostMapping("/withdrawn/{id}")
    public ResponseEntity<Message<String>> withdrawn(@PathVariable Long id, @RequestBody String password) {
        return memberService.withdrawn(id, password);

    }


}
