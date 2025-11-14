package com.scheduleprojectskilled.member.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MemberListResponse {
    private final Long id;
    private final String memberName;
    private final String memberEmail;
    private final String memberCondition;
    private final LocalDateTime createDatetime;
    private final LocalDateTime updateDatetime;
}
