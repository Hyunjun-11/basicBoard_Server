package com.example.basicboard.member.controller;


import com.example.basicboard.common.util.Message;
import com.example.basicboard.member.dto.MemberRequestDTO;
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



    @GetMapping("/")
    public ResponseEntity<Message> readAll(){

        return memberService.readAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Message> readById(@PathVariable Long id){

        return memberService.readById(id);

    }

    @PostMapping("/singnUp")
    public ResponseEntity<Message> singUp(@RequestBody SignUpRequestDTO requestDTO){


        return memberService.signUp(requestDTO);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Message> signIn(@RequestBody MemberRequestDTO requestDTO){
        return memberService.signIn(requestDTO);
    }

}
