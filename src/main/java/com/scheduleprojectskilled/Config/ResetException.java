package com.scheduleprojectskilled.Config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResetException extends RuntimeException{
    private final HttpStatus status;

    public ResetException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
