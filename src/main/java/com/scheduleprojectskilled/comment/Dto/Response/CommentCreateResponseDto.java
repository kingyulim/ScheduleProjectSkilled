package com.scheduleprojectskilled.comment.Dto.Response;

import com.scheduleprojectskilled.comment.CommentEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateResponseDto {
    private final Long commentId;
    private final String commentContent;
    private final String commentWriName;
    private final String commentScheduleTitle;
    private final String commentCreateMessage;
}
