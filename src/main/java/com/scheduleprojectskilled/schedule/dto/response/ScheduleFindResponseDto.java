package com.scheduleprojectskilled.schedule.dto.response;

import com.scheduleprojectskilled.comment.CommentEntity;
import com.scheduleprojectskilled.comment.Dto.Response.CommentFindResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScheduleFindResponseDto {
    private final Long id;
    private final String wriName;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createDatetime;
    private final LocalDateTime updateDatetime;
    private final List<CommentFindResponseDto> commentsList;
}
