package xyz.tomorrowlearncamp.outsourcing.domain.order.enums;

import lombok.Getter;

@Getter
public enum ErrorOrderMessage {
    CANNOT_CANCEL_ORDER("취소할 수 없는 주문입니다."),
    ONLY_USER_CAN_CANCEL("본인의 주문만 취소할 수 있습니다."),
    ORDER_NOT_FOUND("주문 내역이 존재하지 않습니다."),
    MINIMUM_ORDER_NOT_MET("최소 주문 금액 이상 주문해야 합니다."),
    NOT_BUSINESS_HOURS("현재 영업 시간이 아닙니다."),

    NOT_OWNER_ORDER("본인 가게 주문만 처리할 수 있습니다."),
    ALREADY_DELIVERING_OR_COMPLETED("이미 배달이 시작되었거나 완료된 주문입니다."),
    MUST_BE_ACCEPTED_BEFORE_COOKING("주문 접수 후에 조리를 시작할 수 있습니다."),
    MUST_BE_COOKING_BEFORE_DELIVERY("조리를 시작한 주문만 배달할 수 있습니다."),
    MUST_BE_DELIVERING_BEFORE_COMPLETE("배달중인 주문만 배달 완료 처리를 할 수 있습니다."),
    ALREADY_COMPLETED("이미 배달이 완료된 주문입니다.");

    private final String message;

    ErrorOrderMessage(String message) {
        this.message = message;
    }

}
