package com.scheduleprojectskilled.schedule.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteScheduleResponseDto {
    private final Long scheduleId;
    private final String scheduleTitle;
    private final Long memberId;
    private final String deleteMessage;
}
