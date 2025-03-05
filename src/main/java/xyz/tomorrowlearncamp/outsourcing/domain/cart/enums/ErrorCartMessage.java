package xyz.tomorrowlearncamp.outsourcing.domain.cart.enums;

import lombok.Getter;

@Getter
public enum ErrorCartMessage {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."), // TODO: UserErrorMessage로 이동
    CART_ITEM_NOT_FOUND("장바구니에서 메뉴를 찾을 수 없습니다.");

    private final String message;

    ErrorCartMessage(String message) {
        this.message = message;
    }
}
