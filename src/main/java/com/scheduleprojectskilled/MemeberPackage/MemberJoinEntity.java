package com.scheduleprojectskilled.MemeberPackage;

import com.scheduleprojectskilled.Config.DatetimeHandler;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinEntity extends DatetimeHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 30, nullable = false)
    private String memberEmail;

    @Column(length = 20, nullable = false)
    private String memberPassword;

    @Column(length = 20, nullable = false)
    private String memberCondition = "member";

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime DeleteDatetime;

    public MemberJoinEntity(
            String memberNameParm,
            String memberEmailParm,
            String memberPasswordParm
    ) {
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
        this.memberPassword = memberPasswordParm;
    }

    /**
     * 회원정보 업데이트 세터
     * @param memberEmailParm 변경될 이메일 입력값 파라미터
     * @param memberPasswordParm 변경될 비밀번호 입력값 파라미터
     */
    public void memberUpdate(String memberEmailParm, String memberPasswordParm) {
        this.memberEmail = memberEmailParm;
        this.memberPassword = memberPasswordParm;
    }

    /**
     * 탈퇴 회원 상태 업데이트 세터
     * @param memberConditionParm 탈퇴회원 필드 값 변경 파라미터
     */
    public void memberDeleteCondition(String memberConditionParm, LocalDateTime DeleteDatetimeParm) {
        this.memberCondition = memberConditionParm;
        this.DeleteDatetime = DeleteDatetimeParm;
    }
}
