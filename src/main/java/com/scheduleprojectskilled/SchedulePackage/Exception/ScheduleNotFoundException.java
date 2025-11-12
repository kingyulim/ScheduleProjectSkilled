package com.scheduleprojectskilled.SchedulePackage.Exception;

import com.scheduleprojectskilled.Config.ResetException;
import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ResetException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
