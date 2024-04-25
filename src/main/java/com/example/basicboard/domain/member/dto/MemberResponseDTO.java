package com.example.basicboard.domain.member.dto;


import com.example.basicboard.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseDTO {

    private Long id;

    private String memberId;

    private String memberName;

    private Member.Gender gender;

    private String memberPhone;


    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.gender = member.getGender();
        this.memberPhone = member.getMemberPhone();
    }
}
