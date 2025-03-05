package xyz.tomorrowlearncamp.outsourcing.domain.menu.enums;

import lombok.Getter;

@Getter
public enum MenuErrorMessage {
    NOT_FOUND_MENU("메뉴를 찾을 수 없습니다."),
    NOT_ALLOWED_ADD_MENU("본인의 가게만 메뉴를 추가 할 수 있습니다."),
    NOT_ALLOWED_VIEW_MENU("본인의 가게만 메뉴를 조회 할 수 있습니다."),
    NOT_ALLOWED_UPDATE_MENU("본인의 가게만 메뉴를 수정 할 수 있습니다."),
    NOT_ALLOWED_REMOVE_MENU("본인의 가게만 메뉴를 삭제 할 수 있습니다.")
    ;

    private final String errorMessage;

    MenuErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
