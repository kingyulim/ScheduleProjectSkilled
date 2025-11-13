package com.scheduleprojectskilled.Common.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageEnum {
    NO_MEMBER_ID(HttpStatus.NOT_FOUND, "해당 ID의 회원을 찾을 수 없습니다."),
    NO_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 되지 않았습니다."),
    NO_MEMBER_KEY_MATCHING(HttpStatus.UNAUTHORIZED, "회원 고유 번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
