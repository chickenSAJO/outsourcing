package xyz.tomorrowlearncamp.outsourcing.domain.menu.enums;

import lombok.Getter;

@Getter
public enum MenuErrorMessage {
    NOT_FOUND_MENU("메뉴를 찾을 수 없습니다."),
    ;

    private final String errorMessage;

    MenuErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
