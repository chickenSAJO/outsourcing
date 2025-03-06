package xyz.tomorrowlearncamp.outsourcing.auth;

public final class AuthValidationMessages {

    public static final String EMAIL_VALIDATION_MESSAGE = "유효한 이메일 형식이 아닙니다.";
    public static final String PASSWORD_VALIDATION_MESSAGE = "비밀번호는 최소 8글자 이상이어야 하며, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.";

    public static final String EMAIL_DUPLICATION_MESSAGE = "이미 존재하는 이메일입니다.";
    public static final String EMAIL_OR_PASSWORD_DIFF_MESSAGE = "이메일 또는 비밀번호가 일치하지 않습니다.";

}
