package xyz.tomorrowlearncamp.outsourcing.domain.cart.enums;

import lombok.Getter;

@Getter
public enum ErrorCartMessage {
    CART_ITEM_NOT_FOUND("장바구니에서 메뉴를 찾을 수 없습니다."),
    EMPTY_CART("장바구니가 비었습니다.");

    private final String message;

    ErrorCartMessage(String message) {
        this.message = message;
    }
}
