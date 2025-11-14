package com.scheduleprojectskilled.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageEnum {
    NO_MEMBER_ID(HttpStatus.NOT_FOUND, "해당 ID의 회원을 찾을 수 없습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "해당 스케줄을 찾을 수 없습니다."),
    NO_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 되지 않았습니다."),
    MEMBER_CHECK(HttpStatus.UNAUTHORIZED, "회원정보가 맞지 않습니다."),
    NO_SESSION(HttpStatus.UNAUTHORIZED, "세선이 조회되지 않습니다."),
    LOGIN_CHECK(HttpStatus.FORBIDDEN, "로그인 되었습니다."),
    NO_MEMBER_KEY_MATCHING(HttpStatus.UNAUTHORIZED, "회원 고유 번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    EMPTY_EMAIL(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
