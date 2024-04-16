package com.example.basicboard.domain.member.dto;

import com.example.basicboard.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class MemberRequestDTO {
    private String memberName;
    private String memberPhone;
    private Member.Gender gender;
    private String password;
}
