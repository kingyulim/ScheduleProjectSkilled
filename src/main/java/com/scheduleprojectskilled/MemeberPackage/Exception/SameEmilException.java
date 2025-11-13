package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Common.Exception.CustomException;
import org.springframework.http.HttpStatus;

public class SameEmilException extends CustomException {
    public SameEmilException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
