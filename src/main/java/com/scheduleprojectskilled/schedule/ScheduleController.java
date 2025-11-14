package com.scheduleprojectskilled.schedule;

import com.scheduleprojectskilled.common.exception.CustomException;
import com.scheduleprojectskilled.common.exception.ExceptionMessageEnum;
import com.scheduleprojectskilled.member.dto.response.SessionResponse;
import com.scheduleprojectskilled.schedule.dto.request.ScheduleCreateRequestDto;
import com.scheduleprojectskilled.schedule.dto.request.ScheduleUpdateRequestDto;
import com.scheduleprojectskilled.schedule.dto.response.CreateScheduleResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleFindResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleUpdateResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * schedules 테이블 생성 controller
     * @param request 입력된 값 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @PostMapping("/createSchedule")
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @Valid @RequestBody ScheduleCreateRequestDto request,
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
                .body(scheduleService.createSchedule(thisSession.getId(), thisSession.getMemberName(), request));
    }

    /**
     * schedules 테이블 생성된 데이터 지정 단건조회
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleFindResponseDto> findOneSchedule(@PathVariable("scheduleId") Long scheduleId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.findoneSchedule(scheduleId));
    }

    /**
     * schedules 테이블 생성된 데이터 다건 조회
     * @return 검사된 데이터 JSON 반환
     */
    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleFindResponseDto>> findAllSchedule(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.findAllSchedule());
    }

    /**
     * schedules 테이블 생성된 데이터 지정 업데이트
     * @param scheduleId schedules 고유 번호 파라미터
     * @param request 입력된 값 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponseDto> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequestDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.updateSchedule(scheduleId, request));
    }

    /**
     * schedules 테이블 생성된 데이터 지정 삭제
     * @param scheduleId schedules 고유 번호 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
