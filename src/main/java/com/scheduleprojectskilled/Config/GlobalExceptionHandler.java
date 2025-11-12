package com.scheduleprojectskilled.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * ScheduleException 커스텀 예외처리(id 값 검사)
     * @param ex ScheduleException 메세지 파라미터
     * @return 에러 메세지 String 반환
     */
    @ExceptionHandler(ResetException.class)
    public ResponseEntity<String> handleScheduleException(ResetException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

    /**
     * DTO Request Valid 예외처리
     * @param ex 검증 실패 관련 상세 정보 파라미터
     * @return 에러 메세지 String 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
