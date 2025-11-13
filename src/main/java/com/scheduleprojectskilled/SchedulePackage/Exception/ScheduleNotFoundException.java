package com.scheduleprojectskilled.SchedulePackage.Exception;

import com.scheduleprojectskilled.Common.Exception.CustomException;
import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends CustomException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
