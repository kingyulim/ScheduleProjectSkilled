package com.scheduleprojectskilled.MemeberPackage;

import com.scheduleprojectskilled.Config.DatetimeHandler;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public MemberJoinEntity(
            String memberNameParm,
            String memberEmailParm,
            String memberPasswordParm
    ) {
        this.memberName = memberNameParm;
        this.memberEmail = memberEmailParm;
        this.memberPassword = memberPasswordParm;
    }

    public void memberUpdate(String memberEmailParm, String memberPasswordParm) {
        this.memberEmail = memberEmailParm;
        this.memberPassword = memberPasswordParm;
    }
}
