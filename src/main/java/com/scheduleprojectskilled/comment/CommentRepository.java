package com.scheduleprojectskilled.comment;

import com.scheduleprojectskilled.comment.Dto.Response.CommentFindResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    /**
     * 스케줄 고유 번호 기준 카운트 반환 쿼리 메서드
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @return SELECT COUNT(1) FROM comments WHERE schedule_id = scheduleId
     */
    long countByScheduleId(Long scheduleId);

    /**
     * 쿼리 메서드 강제 dto 변환
     * @param scheduleId 스케줄 고유 번호
     * @param memberId 회원 고유 번호
     * @return SELECT
     *     c.schedule_id AS schedule_id,
     *     c.member_id AS member_id,
     *     c.comment_name AS comment_name,
     *     s.schedule_title AS schedule_title,
     *     c.comment_content AS comment_content,
     *     c.create_datetime AS create_datetime,
     *     c.update_datetime AS update_datetime
     * FROM comment c
     * JOIN schedule s ON c.schedule_id = s.id
     * JOIN member m ON c.member_id = m.id
     * WHERE c.schedule_id = :scheduleId
     *   AND c.member_id = :memberId
     * ORDER BY c.create_datetime DESC;
     */
    @Query("""
    SELECT new com.scheduleprojectskilled.comment.Dto.Response.CommentFindResponseDto(
        c.schedule.id,
        c.member.id,
        c.commentName,
        c.schedule.scheduleTitle,
        c.commentContent,
        c.createDatetime,
        c.updateDatetime
    )
    FROM CommentEntity c
    WHERE c.schedule.id = :scheduleId AND c.member.id = :memberId
""")
    List<CommentFindResponseDto> findBySchedule_IdAndMember_Id(Long scheduleId, Long memberId);
}
