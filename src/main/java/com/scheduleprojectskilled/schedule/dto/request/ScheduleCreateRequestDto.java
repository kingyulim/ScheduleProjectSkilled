package com.scheduleprojectskilled.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleCreateRequestDto {
    @NotBlank(message = "제목이 입력되지 않았습니다.")
    @Size(
            min = 1,
            max = 50,
            message = "제목은 1 ~ 50자 로 입력해주세요."
    )
    private String scheduleTitle;

    //------------

    @NotBlank(message = "내용이 입력되지 않았습니다.")
    //@Pattern(regexp = "^<([a-z]+)([^<]+)(>(.)<\\/\\1>|\\s*\\/>)$")
    private String scheduleContent;
}
