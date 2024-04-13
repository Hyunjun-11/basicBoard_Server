package com.example.basicboard.member.dto;


import com.example.basicboard.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseDTO {


    private String memberName;

    private Member.Gender gender;

    private String memberPhone;


    public MemberResponseDTO(Member member) {

        memberName = member.getMemberName();
        gender = member.getGender();
        memberPhone = member.getMemberPhone();
    }
}
