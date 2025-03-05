package xyz.tomorrowlearncamp.outsourcing.domain.order.enums;

import lombok.Getter;

@Getter
public enum ErrorOrderMessage {
    CANNOT_CANCEL_ORDER("취소할 수 없는 주문입니다."),
    ONLY_OWNER_CAN_ACCEPT("본인의 가게 주문만 수락할 수 있습니다."),
    ONLY_OWNER_CAN_REJECT("본인의 가게 주문만 거절할 수 있습니다."),
    ONLY_USER_CAN_CANCEL("본인의 주문만 취소할 수 있습니다."),
    ORDER_NOT_FOUND("주문 내역이 존재하지 않습니다."),
    MINIMUM_ORDER_NOT_MET("최소 주문 금액 이상 주문해야 합니다."),
    NOT_BUSINESS_HOURS("현재 영업 시간이 아닙니다.");

    private final String message;

    ErrorOrderMessage(String message) {
        this.message = message;
    }

}
