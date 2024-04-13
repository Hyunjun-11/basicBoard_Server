package com.example.basicboard.member.controller;


import com.example.basicboard.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("api/member")
public class MemberController {



    @GetMapping("/")
    public ResponseEntity<Member> readAll(){
        return null;
    }

}
