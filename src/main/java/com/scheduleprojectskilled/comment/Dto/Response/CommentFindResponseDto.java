package com.scheduleprojectskilled.comment.Dto.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentFindResponseDto {
    private final Long scheduleId;
    private final Long memberId;
    private final String commentWriName;
    private final String scheduleTitle;
    private final String commentContent;
    private final LocalDateTime createDatetime;
    private final LocalDateTime updateDatetime;
}
