package com.scheduleprojectskilled.SchedulePackage;

import com.scheduleprojectskilled.SchedulePackage.Dto.Request.CreateScheduleRequest;
import com.scheduleprojectskilled.SchedulePackage.Dto.Request.UpdateScheduleRequest;
import com.scheduleprojectskilled.SchedulePackage.Dto.Response.CreateScheduleResponse;
import com.scheduleprojectskilled.SchedulePackage.Dto.Response.FindScheduleResponse;
import com.scheduleprojectskilled.SchedulePackage.Dto.Response.UpdateScheduleResponse;
import com.scheduleprojectskilled.SchedulePackage.Exception.ScheduleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private String noScheduleDataMessage = "해당 번호와 일치하는 스케줄이 없습니다.";

    /**
     * schedule 테이블 비지니스 로직 처리
     * @param request 입력된 값 파라미터
     * @return Controller에 보여줄 CreateScheduleResponse반환
     */
    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
        ScheduleEntity schedule = new ScheduleEntity(
                request.getWriName(),
                request.getScheduleTitle(),
                request.getScheduleContent()
        );

        ScheduleEntity saveSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                saveSchedule.getId(),
                saveSchedule.getWriName(),
                saveSchedule.getScheduleTitle(),
                saveSchedule.getScheduleContent(),
                saveSchedule.getCreateDatetime(),
                saveSchedule.getUpdateDatetime()
        );
    }

    /**
     * Schedule 단건 조회 비지니스 로직 처리
     * @param scheduleId Schedule 데이터 고유 번호 파라미터
     * @return Controller에 보여줄 FindScheduleResponse반환
     */
    @Transactional(readOnly = true)
    public FindScheduleResponse findoneSchedule(Long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(noScheduleDataMessage)
        );

        return new FindScheduleResponse(
                schedule.getId(),
                schedule.getWriName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getCreateDatetime(),
                schedule.getUpdateDatetime()
        );
    }

    /**
     * Schedule 다건 조회 비지니스 로직 처리
     * @return Controller에 보여줄 schedules 배열 반환
     */
    @Transactional(readOnly = true)
    public List<FindScheduleResponse> findAllSchedule() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(s -> new FindScheduleResponse(
                        s.getId(),
                        s.getWriName(),
                        s.getScheduleTitle(),
                        s.getScheduleContent(),
                        s.getCreateDatetime(),
                        s.getUpdateDatetime()
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
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(noScheduleDataMessage)
        );

        schedule.scheduleUpdate(
                request.getScheduleTitle(),
                request.getScheduleContent()
        );

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getWriName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getUpdateDatetime()
        );
    }

    /**
     * schedule 삭제 비니지스 로직
     * @param scheduleId schedule 고유 번호 파라미터
     */
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        /*
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException(noScheduleDataMessage)
        );
         */

        boolean exists = scheduleRepository.existsById(scheduleId);

        if (exists) {
            scheduleRepository.deleteById(scheduleId);
        } else {
            throw new ScheduleNotFoundException("존재하지 않은 댓글입니다.");
        }
    }
}
