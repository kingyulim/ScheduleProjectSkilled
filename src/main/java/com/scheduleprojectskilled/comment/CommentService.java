package com.scheduleprojectskilled.comment;

import com.scheduleprojectskilled.comment.Dto.Request.CommentCreateRequestDto;
import com.scheduleprojectskilled.comment.Dto.Request.CommentDeleteRequestDto;
import com.scheduleprojectskilled.comment.Dto.Request.CommentUpdateRequestDto;
import com.scheduleprojectskilled.comment.Dto.Response.CommentCreateResponseDto;
import com.scheduleprojectskilled.comment.Dto.Response.CommentDeleteResponseDto;
import com.scheduleprojectskilled.comment.Dto.Response.CommentFindResponseDto;
import com.scheduleprojectskilled.comment.Dto.Response.CommentUpdateResponseDto;
import com.scheduleprojectskilled.common.exception.CustomException;
import com.scheduleprojectskilled.common.exception.ExceptionMessageEnum;
import com.scheduleprojectskilled.member.MemberJoinEntity;
import com.scheduleprojectskilled.member.MemberRepository;
import com.scheduleprojectskilled.schedule.ScheduleEntity;
import com.scheduleprojectskilled.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 생성 비지니스 로직 처리
     * @param memberId 회원 고유 번호 파라미터
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @param request 댓글 입력 파라미터
     * @return CommentCreateResponseDto 데이터 반환
     */
    @Transactional
    public CommentCreateResponseDto createComment(
            Long memberId,
            Long scheduleId,
            CommentCreateRequestDto request
    ) {
        MemberJoinEntity memberEntity = memberRepository
                .findById(memberId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        ScheduleEntity scheduleEntity = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
                );

        CommentEntity comment = new CommentEntity(
                memberEntity.getMemberName(),
                request.getCommentContent(),
                memberEntity,
                scheduleEntity
        );

        CommentEntity saveComment = commentRepository.save(comment);

        return new CommentCreateResponseDto(
                saveComment.getId(),
                saveComment.getCommentContent(),
                memberEntity.getMemberName(),
                scheduleEntity.getScheduleTitle(),
                memberEntity.getMemberName() + "님이 " + scheduleEntity.getScheduleTitle() + "에 댓글을 남겼습니다."
        );
    }

    /**
     * 내가 쓴 댓글 호출
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @param memberId 회원 고유 번호 파라미터
     * @return CommentFindResponseDto list 형식의 데이터로 반환
     */
    @Transactional(readOnly = true)
    public List<CommentFindResponseDto> findMyComment(
            Long scheduleId,
            Long memberId
    ) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
                );

        memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        List<CommentFindResponseDto> myComments = commentRepository.findBySchedule_IdAndMember_Id(scheduleId, memberId);

        return myComments
                .stream()
                .map(c -> new CommentFindResponseDto(
                        c.getScheduleId(),
                        c.getMemberId(),
                        c.getCommentWriName(),
                        c.getScheduleTitle(),
                        c.getCommentContent(),
                        c.getCreateDatetime(),
                        c.getUpdateDatetime()
                ))
                .toList();
    }

    /**
     * 내가 작성한 댓글 업데이트 비지니스 로직 처리
     * @param commentId 댓글 고유 번호 파라미터
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @param memberId 회원 고유 번호 파라미터
     * @param request 수정 될 댓글 입력값 파라미터
     * @return CommentUpdateResponseDto 데이터 반환
     */
    @Transactional
    public CommentUpdateResponseDto UpdateComment(
            Long scheduleId,
            Long commentId,
            Long memberId,
            CommentUpdateRequestDto request
    ) {
        ScheduleEntity schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
                );

        CommentEntity comment = commentRepository
                .findById(commentId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_COMMENT)
                );

        comment.commentUpdate(request.getCommentContent());

        return new CommentUpdateResponseDto(
                comment.getId(),
                memberId,
                request.getCommentContent(),
                comment.getCommentName(),
                schedule.getScheduleTitle(),
                comment.getCommentName()
        );
    }

    /**
     * 댓글 삭제 비지니스 로직 처리
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @param memberId 회원 고유 번호 파라미터
     * @param request 댓글 삭제 고유 번호 입력 파라미터
     * @return CommentDeleteResponseDto 데이터 반환
     */
    @Transactional
    public CommentDeleteResponseDto deleteComment(
            Long scheduleId,
            CommentDeleteRequestDto request,
            Long memberId
    ) {
        ScheduleEntity schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
                );

        CommentEntity comment = commentRepository
                .findById(request.getCommentId())
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_COMMENT)
                );

        memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.MEMBER_FORBIDDEN)
                );

        commentRepository.deleteById(request.getCommentId());

        return new CommentDeleteResponseDto(
                schedule.getId(),
                comment.getId(),
                schedule.getScheduleTitle() + "의 댓글 " + request.getCommentId() + "가 삭제 되었습니다."
        );
    }
}
