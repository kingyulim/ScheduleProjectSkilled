package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Config.ResetException;
import org.springframework.http.HttpStatus;

public class SameEmilException extends ResetException {
    public SameEmilException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
