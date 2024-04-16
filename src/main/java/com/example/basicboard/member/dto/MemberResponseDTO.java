package com.example.basicboard.member.dto;


import com.example.basicboard.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class MemberResponseDTO {

    private Long id;

    private String memberName;

    private Member.Gender gender;

    private String memberPhone;


    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.memberName = member.getMemberName();
        this.gender = member.getGender();
        this.memberPhone = member.getMemberPhone();
    }
}
