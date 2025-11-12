package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Config.ResetException;
import org.springframework.http.HttpStatus;

public class NoFindMemberId extends ResetException {
    public NoFindMemberId(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
