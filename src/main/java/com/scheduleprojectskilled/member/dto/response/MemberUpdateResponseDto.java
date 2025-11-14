package com.scheduleprojectskilled.member.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberUpdateResponseDto {
    private final Long id;
    private final String memberName;
    private final String updateMessage;
}
