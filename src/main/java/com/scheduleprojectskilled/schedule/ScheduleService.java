package com.scheduleprojectskilled.schedule;

import com.scheduleprojectskilled.comment.CommentRepository;
import com.scheduleprojectskilled.comment.Dto.Response.CommentFindResponseDto;
import com.scheduleprojectskilled.common.exception.CustomException;
import com.scheduleprojectskilled.common.exception.ExceptionMessageEnum;
import com.scheduleprojectskilled.member.MemberJoinEntity;
import com.scheduleprojectskilled.member.MemberRepository;
import com.scheduleprojectskilled.schedule.dto.request.ScheduleCreateRequestDto;
import com.scheduleprojectskilled.schedule.dto.request.ScheduleUpdateRequestDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleCreateResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleDeleteResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleFindResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /**
     * schedule 테이블 비지니스 로직 처리
     * @param request 입력된 값 파라미터
     * @return Controller에 보여줄 CreateScheduleResponse반환
     */
    @Transactional
    public ScheduleCreateResponseDto createSchedule(Long memberId, String memberName, ScheduleCreateRequestDto request) {
        MemberJoinEntity member = memberRepository
                .findById(memberId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        ScheduleEntity schedule = new ScheduleEntity(
                member.getMemberName(),
                request.getScheduleTitle(),
                request.getScheduleContent(),
                member
        );

        ScheduleEntity saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(
                saveSchedule.getId(),
                memberId,
                memberName,
                saveSchedule.getScheduleTitle(),
                saveSchedule.getScheduleContent(),
                saveSchedule.getCreateDatetime()
        );
    }

    /**
     * Schedule 단건 조회 비지니스 로직 처리
     * @param scheduleId Schedule 데이터 고유 번호 파라미터
     * @return Controller에 보여줄 FindScheduleResponse반환
     */
    @Transactional(readOnly = true)
    public ScheduleFindResponseDto findOneSchedule(Long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
        );

        return new ScheduleFindResponseDto(
                schedule.getId(),
                schedule.getWriName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getCreateDatetime(),
                schedule.getUpdateDatetime(),
                commentRepository.findBySchedule_IdAndMember_Id(schedule.getId(), schedule.getMember().getId())
        );
    }

    /**
     * Schedule 다건 조회 비지니스 로직 처리
     * @return Controller에 보여줄 schedules 배열 반환
     */
    @Transactional(readOnly = true)
    public List<ScheduleFindResponseDto> findAllSchedule() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(s -> new ScheduleFindResponseDto(
                        s.getId(),
                        s.getWriName(),
                        s.getScheduleTitle(),
                        s.getScheduleContent(),
                        s.getCreateDatetime(),
                        s.getUpdateDatetime(),
                        commentRepository.findBySchedule_IdAndMember_Id(s.getId(), s.getMember().getId())
                ))
                .toList();
    }

    /**
     * 내가 쓴 스케줄 다건 조회
     * @param memberId 세션 고유 회원 번호 파라미터
     * @param memberName 세션 회원 이름 파라미터
     * @return 필터링 된 FindScheduleResponseDto 데이터 반환
     */
    @Transactional(readOnly = true)
    public List<ScheduleFindResponseDto> findMySchedule(Long memberId, String memberName) {
        List<ScheduleEntity> myScheduleList = scheduleRepository.findByMemberIdAndWriName(memberId, memberName);

        if (myScheduleList.isEmpty()) {
            throw new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE);
        }

        return myScheduleList
                .stream()
                .map(m -> new ScheduleFindResponseDto(
                    m.getId(),
                    m.getWriName(),
                    m.getScheduleTitle(),
                    m.getScheduleContent(),
                    m.getCreateDatetime(),
                    m.getUpdateDatetime(),
                    commentRepository.findBySchedule_IdAndMember_Id(m.getId(), m.getMember().getId())
                ))
                .toList();
    }

    /**
     * Schedule 지정 데이터 업데이트
     * @param scheduleId Schedule 고유 번호 파라미터
     * @param request 입력된 값 파라미터
     * @return Controller에 보여줄 UpdateScheduleResponse반환
     */
    @Transactional
    public ScheduleUpdateResponseDto updateSchedule(Long scheduleId, Long memberId, ScheduleUpdateRequestDto request) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
        );

        boolean updateCheck = scheduleRepository.existsByIdAndMemberId(scheduleId, memberId);

        if (!updateCheck) {
           throw new CustomException(ExceptionMessageEnum.MEMBER_FORBIDDEN);
        }

        String prevTitle = schedule.getScheduleTitle();

        schedule.scheduleUpdate(
                request.getScheduleTitle(),
                request.getScheduleContent()
        );

        return new ScheduleUpdateResponseDto(
                schedule.getId(),
                schedule.getWriName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getUpdateDatetime(),
                schedule.getWriName() + "님의 \"" + prevTitle + "\"스케줄이 수정 되었습니다."
        );
    }

    /**
     * schedule 삭제 비니지스 로직
     * @param scheduleId schedule 고유 번호 파라미터
     */
    @Transactional
    public ScheduleDeleteResponseDto deleteSchedule(Long scheduleId, Long memberId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
        );

        /**
         * 댓글이 1개 이상 달려 있다면 예외 처리
         */
        long commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount > 0) {
            throw new CustomException(ExceptionMessageEnum.NOT_DELETE_SCHEDULE);
        }

        boolean deleteCheck = scheduleRepository.existsByIdAndMemberId(scheduleId, memberId);
        if (!deleteCheck) {
            throw new CustomException(ExceptionMessageEnum.MEMBER_FORBIDDEN);
        }

        String prevTitle = schedule.getScheduleTitle();

        scheduleRepository.deleteById(scheduleId);

        return new ScheduleDeleteResponseDto(
                scheduleId,
                schedule.getScheduleTitle(),
                memberId,
                prevTitle + " 게시물이 삭제 되었습니다."
        );
    }
}
