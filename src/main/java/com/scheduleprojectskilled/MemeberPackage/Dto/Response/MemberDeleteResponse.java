package com.scheduleprojectskilled.MemeberPackage.Dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDeleteResponse {
    private final Long id;
    private final String memberName;
    private final String memberEmail;

    public MemberDeleteResponse(
            Long idParm,
            String memberNameParm,
            String memberEmailParm
    ) {
        this.id = idParm;
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
    }
}
