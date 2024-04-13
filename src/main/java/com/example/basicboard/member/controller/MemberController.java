package com.example.basicboard.member.controller;


import com.example.basicboard.common.util.Message;
import com.example.basicboard.member.dto.MemberRequestDTO;
import com.example.basicboard.member.entity.Member;
import com.example.basicboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;



    @GetMapping("/")
    public ResponseEntity<Message> readAll(){

        return memberService.readAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Message> readById(@PathVariable Long id){

        return memberService.readById(id);

    }

    @PostMapping("/singnUp")
    public ResponseEntity<Message> singUp(@RequestBody MemberRequestDTO requestDTO){


        return null;
    }

}
