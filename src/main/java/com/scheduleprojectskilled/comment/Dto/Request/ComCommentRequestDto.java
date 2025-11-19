package com.scheduleprojectskilled.comment.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ComCommentRequestDto {
    @NotBlank(message = "내용이 입력되지 않았습니다.")
    private String commentContent;
}
