package com.scheduleprojectskilled.Config;

public enum ExceptionMessageEnum {
    NO_MEMBER_MESSAGE("회원정보가 없습니다."),
    NO_SCHEDULE_MESSAGE("해당 번호와 일치하는 스케줄이 없습니다.");

    private String message;

    private ExceptionMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
