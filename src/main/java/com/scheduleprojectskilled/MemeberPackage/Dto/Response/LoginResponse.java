package com.scheduleprojectskilled.MemeberPackage.Dto.Response;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final Long id;
    private final String memberName;
    private final String memberEmail;

    public LoginResponse(
            Long idParm,
            String memberNameParm,
            String memberEmailParm
    ) {
        this.id = idParm;
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
    }
}
