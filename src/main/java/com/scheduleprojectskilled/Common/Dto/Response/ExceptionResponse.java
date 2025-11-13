package com.scheduleprojectskilled.Common.Dto.Response;

import com.scheduleprojectskilled.Common.Exception.ExceptionMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private final int status;
    private final String code;
    private final String message;

    public ExceptionResponse(ExceptionMessageEnum errorCode, String message) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = message;
    }
}
