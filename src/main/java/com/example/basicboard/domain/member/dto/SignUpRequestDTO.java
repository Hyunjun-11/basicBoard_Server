package com.example.basicboard.domain.member.dto;

import com.example.basicboard.domain.member.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {


    @NotEmpty(message = "아이디를 입력해주세요")
    private String memberId;

    @Pattern(regexp = "^(?=.*[가-힣a-zA-Z])[가-힣a-zA-Z0-9]{3,8}$", message = "닉네임은 3~8자 이내 한글or영어(대소문자),숫자(선택) 범위에서 입력해주세요. 특수문자는 포함할 수 없습니다.")
    private String memberName;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d!@#$%^&*()_-]{5,12}$", message = "비밀번호는 5~12자 이내 영어(소문자),숫자,특수기호(선택) 범위에서 입력해야합니다.")
    private String password;

    @Pattern(regexp = "\\d", message = "전화번호는 숫자만 입력해주세요")
    private String memberPhone;

    private Member.Gender gender;

}
