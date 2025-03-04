package xyz.tomorrowlearncamp.outsourcing.domain.store.enums;

import lombok.Getter;

@Getter
public enum StoreErrorMessage {
    NOT_FOUND_STORE("가게를 찾을 수 없습니다."),
    STORE_THREE_OVER("가게는 최대 3개만 등록 가능합니다."),
    ;

    private final String errorMessage;

    StoreErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
