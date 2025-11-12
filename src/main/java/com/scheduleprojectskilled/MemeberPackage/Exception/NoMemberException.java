package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Config.ResetException;
import org.springframework.http.HttpStatus;

public class NoMemberException extends ResetException {
    public NoMemberException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
