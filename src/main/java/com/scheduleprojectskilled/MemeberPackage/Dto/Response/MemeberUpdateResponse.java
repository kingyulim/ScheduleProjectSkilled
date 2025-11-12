package com.scheduleprojectskilled.MemeberPackage.Dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemeberUpdateResponse {
    private final Long id;
    private final String memberName;
    private final String memberEmail;
    private final LocalDateTime updateDatetime;

    public MemeberUpdateResponse(
            Long idParm,
            String memberNameParm,
            String memberEmailParm,
            LocalDateTime updateDatetimeParm
    ) {
        this.id = idParm;
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
        this.updateDatetime = updateDatetimeParm;
    }
}
