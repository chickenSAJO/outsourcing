package xyz.tomorrowlearncamp.outsourcing.auth.dto.request;

public interface ValidationMessages {

    String EMAIL_VALIDATION_MESSAGE = "유효한 이메일 형식이 아닙니다.";
    String PASSWORD_VALIDATION_MESSAGE = "비밀번호는 최소 8글자 이상이어야 하며, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.";

}
