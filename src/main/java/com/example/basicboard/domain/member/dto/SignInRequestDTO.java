package com.example.basicboard.domain.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDTO {
    private String memberId;
    private String password;


}
