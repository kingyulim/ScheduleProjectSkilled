package com.scheduleprojectskilled.schedule;

import com.scheduleprojectskilled.common.entity.DatetimeEntity;
import com.scheduleprojectskilled.member.MemberJoinEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleEntity extends DatetimeEntity {
    // 속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            length = 20,
            nullable = false
    )
    private String wriName;

    @Column(
            length = 50,
            nullable = false
    )
    private String scheduleTitle;

    @Column(
            columnDefinition = "text",
            nullable = false
    )
    private String scheduleContent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberJoinEntity member;

    // 생성자
    public ScheduleEntity (
            String wriNameParm,
            String scheduleTitleParm,
            String scheduleContentParm,
            MemberJoinEntity memberParm
    ) {
        this.wriName = wriNameParm;
        this.scheduleTitle = scheduleTitleParm;
        this.scheduleContent = scheduleContentParm;
        this.member = memberParm;
    }

    // 기능
    public void scheduleUpdate(String scheduleTitleParm,  String scheduleContentParm) {
        this.scheduleTitle = scheduleTitleParm;
        this.scheduleContent = scheduleContentParm;
    }
}
