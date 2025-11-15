package com.scheduleprojectskilled.schedule.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ScheduleUpdateResponseDto {
    private final Long id;
    private final String wriName;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime updateDatetime;
    private final String UpdateMessage;
}
