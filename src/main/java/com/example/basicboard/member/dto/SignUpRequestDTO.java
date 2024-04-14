package com.example.basicboard.member.dto;

import com.example.basicboard.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SignUpRequestDTO {


    private String memberId;

    private String memberName;

    private String password;

    private String memberPhone;

    private Member.Gender gender;

}