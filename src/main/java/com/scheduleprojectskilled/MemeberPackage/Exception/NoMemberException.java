package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Common.Exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoMemberException extends CustomException {
    public NoMemberException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
