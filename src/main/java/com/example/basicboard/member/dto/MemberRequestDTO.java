package com.example.basicboard.member.dto;

import com.example.basicboard.member.entity.Member;
import jakarta.persistence.Column;
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
