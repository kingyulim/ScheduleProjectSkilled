package com.scheduleprojectskilled.Common.Exception;

import com.scheduleprojectskilled.Common.Dto.Response.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        log.error("CustomException 발생 {} : ", e.getMessage());

        return ResponseEntity
                .status(e.getExceptionMessage().getStatus())
                .body(new ExceptionResponse(e.getExceptionMessage(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("유효성 검증 예외 발생: {}", ex.getMessage());

        // 필드별 에러 메시지를 담을 Map
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage())
                );

        // 응답 전체 구조를 담을 Map
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("code", "VALIDATION_ERROR");
        body.put("message", "입력값이 유효하지 않습니다.");
        body.put("errors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    /**
     * ScheduleException 커스텀 예외처리(id 값 검사)
     * @param ex ScheduleException 메세지 파라미터
     * @return 에러 메세지 String 반환
     */
    /*
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleScheduleException(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

     */

    /**
     * DTO Request Valid 예외처리
     * @param ex 검증 실패 관련 상세 정보 파라미터
     * @return 에러 메세지 String 반환
     */
    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

     */
}
