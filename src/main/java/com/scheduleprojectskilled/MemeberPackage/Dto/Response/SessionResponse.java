package com.scheduleprojectskilled.MemeberPackage.Dto.Response;

import lombok.Getter;

@Getter
public class SessionResponse {
    private final Long id;
    private final String memberName;
    private final String memberEmail;

    /**
     * 세션 생성 Response
     * @param idParm 세션 등록 고유값 파라미터
     * @param memberNameParm 세션 등록 회원 이름
     * @param memberEmailParm 세션 등록 회원 이메일
     */
    public SessionResponse(
            Long idParm,
            String memberNameParm,
            String memberEmailParm
    ) {
        this.id = idParm;
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
    }
}
