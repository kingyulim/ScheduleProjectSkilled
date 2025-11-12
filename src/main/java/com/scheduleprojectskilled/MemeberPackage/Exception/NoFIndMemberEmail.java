package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Config.ResetException;
import org.springframework.http.HttpStatus;

public class NoFIndMemberEmail extends ResetException {
    public NoFIndMemberEmail(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
