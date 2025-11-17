package com.scheduleprojectskilled.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ComMemberRequestDto {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @Email(message = "이메일이 형식이 아닙니다.")
    private String memberEmail;

    //------------

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Size(
            min = 1,
            max = 20,
            message = "비밀번호는 1 ~ 20자 로 입력해주세요."
    )
    @Pattern(
            regexp = "^[A-Za-z0-9!@#$%]+$",
            message = "비밀번호 형식이 아닙니다."
    )
    private String memberPassword;
}
