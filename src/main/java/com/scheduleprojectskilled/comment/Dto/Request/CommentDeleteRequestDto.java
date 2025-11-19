package com.scheduleprojectskilled.comment.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CommentDeleteRequestDto {
    @NotBlank(message = "댓글 번호가 비어있으면 안됩니다.")
    @Pattern(regexp = "^[0-9]+$")
    private Long commentId;
}
