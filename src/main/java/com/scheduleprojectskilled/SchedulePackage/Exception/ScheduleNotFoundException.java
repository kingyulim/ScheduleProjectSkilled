package com.scheduleprojectskilled.SchedulePackage.Exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ScheduleException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
