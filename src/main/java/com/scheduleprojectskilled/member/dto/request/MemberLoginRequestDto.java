package com.scheduleprojectskilled.member.dto.request;

import lombok.Getter;

@Getter
public class MemberLoginRequestDto extends ComMemberRequestDto {
    private String memberCondition = "member";
}
