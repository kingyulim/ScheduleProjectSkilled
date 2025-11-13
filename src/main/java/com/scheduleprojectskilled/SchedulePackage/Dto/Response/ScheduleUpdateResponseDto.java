package com.scheduleprojectskilled.SchedulePackage.Dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleUpdateResponseDto {
    private final Long id;
    private final String wriName;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime updateDatetime;

    public ScheduleUpdateResponseDto(
            Long idParm,
            String wriNameParm,
            String scheduleTitleParm,
            String scheduleContentParm,
            LocalDateTime updateDatetimeParm
    ) {
        this.id = idParm;
        this.wriName = wriNameParm;
        this.scheduleTitle = scheduleTitleParm;
        this.scheduleContent = scheduleContentParm;
        this.updateDatetime = updateDatetimeParm;
    }
}
