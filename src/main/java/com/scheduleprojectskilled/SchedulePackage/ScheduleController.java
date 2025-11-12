package com.scheduleprojectskilled.SchedulePackage;

import com.scheduleprojectskilled.SchedulePackage.Dto.Request.CreateScheduleRequest;
import com.scheduleprojectskilled.SchedulePackage.Dto.Request.UpdateScheduleRequest;
import com.scheduleprojectskilled.SchedulePackage.Dto.Response.CreateScheduleResponse;
import com.scheduleprojectskilled.SchedulePackage.Dto.Response.FindScheduleResponse;
import com.scheduleprojectskilled.SchedulePackage.Dto.Response.UpdateScheduleResponse;
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
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request));
    }

    /**
     * schedules 테이블 생성된 데이터 지정 단건조회
     * @param scheduleId 스케줄 고유 번호 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<FindScheduleResponse> findOneSchedule(@PathVariable("scheduleId") Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findoneSchedule(scheduleId));
    }

    /**
     * schedules 테이블 생성된 데이터 다건 조회
     * @return 검사된 데이터 JSON 반환
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<FindScheduleResponse>> findAllSchedule(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllSchedule());
    }

    /**
     * schedules 테이블 생성된 데이터 지정 업데이트
     * @param scheduleId schedules 고유 번호 파라미터
     * @param request 입력된 값 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, request));
    }

    /**
     * schedules 테이블 생성된 데이터 지정 삭제
     * @param scheduleId schedules 고유 번호 파라미터
     * @return 검사된 데이터 JSON 반환
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
