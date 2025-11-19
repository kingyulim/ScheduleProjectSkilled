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
import com.scheduleprojectskilled.member.dto.response.SessionResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 작성 요청 검증
     * @param scheduleId 해당 스케줄 고유 번호 파라미터
     * @param request 댓글 입력 내용 파라미터
     * @param session 세션 파라미터
     * @return CommentCreateResponseDto json 반환
     */
    @PostMapping("/CommentCreate/{scheduleId}")
    public ResponseEntity<CommentCreateResponseDto> createComment(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody CommentCreateRequestDto request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인 확인 예외 처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(
                        thisSession.getId(),
                        scheduleId,
                        request
                ));
    }

    @GetMapping("/commentFind/{scheduleId}/{memberId}")
    public ResponseEntity<List<CommentFindResponseDto>> findComment(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("memberId") Long memberId
    ) {
        List<CommentFindResponseDto> myCommentlist = commentService.findMyComment(scheduleId, memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(myCommentlist);
    }

    /**
     * 내가 작성한 댓글 업데이트 요청 검증
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @param commentId 댓글 고유 번호 파라미터
     * @param request 수정된 댓글 입력 파라미터
     * @param session 세션 파라미터
     * @return CommentUpdateResponseDto json 반환
     */
    @PutMapping("/commentUpdate/{scheduleId}/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequestDto request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인 확인 예외 처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.UpdateComment(commentId, scheduleId, thisSession.getId(), request));
    }

    /**
     * 댓글 삭제 요청 검증
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @param request 댓글 고유 번호 입력값 파라미터
     * @param session 세션 파라미터
     * @return CommentDeleteResponseDto json 반환
     */
    @DeleteMapping("/commentDelete/{scheduleId}")
    public ResponseEntity<CommentDeleteResponseDto> deleteComment(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody CommentDeleteRequestDto request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * 로그인 확인 예외 처리
         */
        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.deleteComment(scheduleId, request, thisSession.getId()));
    }
}
