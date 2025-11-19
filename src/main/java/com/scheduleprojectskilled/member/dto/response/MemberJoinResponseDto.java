package com.scheduleprojectskilled.member.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberJoinResponseDto {
    private final Long id;
    private final String memberName;
}
