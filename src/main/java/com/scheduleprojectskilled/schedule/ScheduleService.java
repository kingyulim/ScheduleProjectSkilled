package com.scheduleprojectskilled.schedule;

import com.scheduleprojectskilled.common.exception.CustomException;
import com.scheduleprojectskilled.common.exception.ExceptionMessageEnum;
import com.scheduleprojectskilled.schedule.dto.request.ScheduleCreateRequestDto;
import com.scheduleprojectskilled.schedule.dto.request.ScheduleUpdateRequestDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleCreateResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleFindResponseDto;
import com.scheduleprojectskilled.schedule.dto.response.ScheduleUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    /**
     * schedule 테이블 비지니스 로직 처리
     * @param request 입력된 값 파라미터
     * @return Controller에 보여줄 CreateScheduleResponse반환
     */
    @Transactional
    public ScheduleCreateResponseDto createSchedule(ScheduleCreateRequestDto request) {
        ScheduleEntity schedule = new ScheduleEntity(
                request.getWriName(),
                request.getScheduleTitle(),
                request.getScheduleContent()
        );

        ScheduleEntity saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(
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
    public ScheduleFindResponseDto findoneSchedule(Long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
        );

        return new ScheduleFindResponseDto(
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
    public List<ScheduleFindResponseDto> findAllSchedule() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(s -> new ScheduleFindResponseDto(
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
    public ScheduleUpdateResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto request) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE)
        );

        schedule.scheduleUpdate(
                request.getScheduleTitle(),
                request.getScheduleContent()
        );

        return new ScheduleUpdateResponseDto(
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
            throw new CustomException(ExceptionMessageEnum.NOT_FOUND_SCHEDULE);
        }
    }
}
