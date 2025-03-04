package xyz.tomorrowlearncamp.outsourcing.domain.menu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MenuErrorCode {
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND,"MENU_NOT_FOUND","메뉴를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    MenuErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status =status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
