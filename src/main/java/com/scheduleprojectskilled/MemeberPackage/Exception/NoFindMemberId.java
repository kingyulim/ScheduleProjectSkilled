package com.scheduleprojectskilled.MemeberPackage.Exception;

import com.scheduleprojectskilled.Common.Exception.CustomException;
import com.scheduleprojectskilled.Common.Exception.ExceptionMessageEnum;


public class NoFindMemberId extends CustomException {
    public NoFindMemberId() {
        super(ExceptionMessageEnum.NO_MEMBER_ID);
    }
}
