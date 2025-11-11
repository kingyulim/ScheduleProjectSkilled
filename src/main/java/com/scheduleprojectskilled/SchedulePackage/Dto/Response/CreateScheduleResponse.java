package com.scheduleprojectskilled.SchedulePackage.Dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {
    private final Long id;
    private final String wriName;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createDatetime;
    private final LocalDateTime updateDatetime;

    public CreateScheduleResponse(
            Long idParm,
            String wriNameParm,
            String scheduleTitleParm,
            String scheduleContentParm,
            LocalDateTime createDatetimeParm,
            LocalDateTime updateDatetimeParm
    ) {
        this.id = idParm;
        this.wriName = wriNameParm;
        this.scheduleTitle = scheduleTitleParm;
        this.scheduleContent = scheduleContentParm;
        this.createDatetime = createDatetimeParm;
        this.updateDatetime = updateDatetimeParm;
    }
}
