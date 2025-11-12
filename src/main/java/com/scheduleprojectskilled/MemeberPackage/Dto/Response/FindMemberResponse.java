package com.scheduleprojectskilled.MemeberPackage.Dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindMemberResponse {
    private final Long id;
    private final String memberName;
    private final String memberEmail;
    private final LocalDateTime createDatetime;
    private final LocalDateTime updateDatetime;

    public FindMemberResponse(
            Long idParm,
            String memberNameParm,
            String memberEmailParm,
            LocalDateTime createDatetimeParm,
            LocalDateTime updateDatetimeParm
    ) {
        this.id = idParm;
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
        this.createDatetime = createDatetimeParm;
        this.updateDatetime = updateDatetimeParm;
    }
}
