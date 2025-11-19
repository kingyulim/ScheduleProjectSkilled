package com.scheduleprojectskilled.comment;

import com.scheduleprojectskilled.common.entity.DatetimeEntity;
import com.scheduleprojectskilled.member.MemberJoinEntity;
import com.scheduleprojectskilled.schedule.ScheduleEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends DatetimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            length = 20,
            nullable = false
    )
    private String commentName;

    @Column(
            columnDefinition = "text",
            nullable = false
    )
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberJoinEntity member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ScheduleEntity schedule;

    public CommentEntity(
            String commentNameParm,
            String commentContentParm,
            MemberJoinEntity memberIdParm,
            ScheduleEntity scheduleIdParm
    ) {
        this.commentName = commentNameParm;
        this.commentContent = commentContentParm;
        this.member = memberIdParm;
        this.schedule = scheduleIdParm;
    }

    public void commentUpdate(String commentContentParm) {
        this.commentContent = commentContentParm;
    }
}
