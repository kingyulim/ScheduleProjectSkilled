package com.scheduleprojectskilled.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberJoinRequestDto extends ComMemberRequestDto {
    @NotBlank(message = "이름이 입력되지 않았습니다.")
    @Size(
            min = 1,
            max = 20,
            message = "이름은 1 ~ 20자 로 입력해주세요."
    )
    @Pattern(
            regexp = "^[가-힣a-zA-Z]+$",
            message = "이름 형식이 아닙니다."
    )
    private String memberName;
}
