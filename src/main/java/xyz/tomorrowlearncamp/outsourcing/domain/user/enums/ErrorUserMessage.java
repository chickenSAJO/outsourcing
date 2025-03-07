package xyz.tomorrowlearncamp.outsourcing.domain.user.enums;

import lombok.Getter;

@Getter
public enum ErrorUserMessage {
    NOT_FOUND_USER("없는 유저입니다."),
    INVALID_PASSWORD("비밀번호를 잘못 입력하셨습니다."),
    UNAUTHORIZED("권한이 없습니다."),
    ;

    private final String errorMassage;

    ErrorUserMessage(String errorMassage) {
        this.errorMassage = errorMassage;
    }
}
