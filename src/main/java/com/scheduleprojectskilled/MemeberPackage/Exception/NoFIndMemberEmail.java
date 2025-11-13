package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Common.Exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoFIndMemberEmail extends CustomException {
    public NoFIndMemberEmail(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
