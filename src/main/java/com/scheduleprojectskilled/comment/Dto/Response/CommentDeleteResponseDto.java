package com.scheduleprojectskilled.comment.Dto.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentDeleteResponseDto {
    private final Long scheduleId;
    private final Long commentId;
    private final String commentDeleteMessage;
}
