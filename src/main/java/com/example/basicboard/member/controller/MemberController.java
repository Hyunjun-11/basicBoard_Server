package com.example.basicboard.member.controller;


import com.example.basicboard.common.util.Message;
import com.example.basicboard.member.dto.MemberRequestDTO;
import com.example.basicboard.member.dto.SignInRequestDTO;
import com.example.basicboard.member.dto.SignUpRequestDTO;
import com.example.basicboard.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;


    //회원전체조회
    @GetMapping("/")
    public ResponseEntity<Message> readAll() {
        return memberService.readAll();
    }

    //회원 단일조회
    @GetMapping("/{id}")
    public ResponseEntity<Message> readById(@PathVariable Long id) {
        return memberService.readById(id);

    }

    //회원가입
    @PostMapping("/singnUp")
    public ResponseEntity<Message> singUp(@RequestBody SignUpRequestDTO requestDTO) {
        return memberService.signUp(requestDTO);
    }

    //회원정보 변경
    @PutMapping("/userInfo/{id}")
    public ResponseEntity<Message> userInfoChange(@PathVariable Long id, @RequestBody MemberRequestDTO requestDTO) {
        return memberService.userInfoChange(id, requestDTO);
    }

    //로그인
    @PostMapping("/signIn")
    public ResponseEntity<Message> signIn(@RequestBody SignInRequestDTO requestDTO) {
        return memberService.signIn(requestDTO);
    }

    //로그아웃
    @PostMapping("/signOut")
    public ResponseEntity<Message> signOut(@RequestBody SignInRequestDTO requestDTO) {
        return memberService.signOut(requestDTO);

    }

    //회원탈퇴(soft-delete)
    @PostMapping("/withdrawn/{id}")
    public ResponseEntity<Message> withdrawn(@PathVariable Long id, @RequestBody String password) {
        return memberService.withdrawn(id, password);

    }


}
