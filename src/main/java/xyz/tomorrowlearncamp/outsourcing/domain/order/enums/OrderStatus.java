package xyz.tomorrowlearncamp.outsourcing.domain.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PENDING("주문 확인전"),
    CANCELED("주문 취소"),
    ACCEPTED("주문 접수"),
    REJECTED("주문 거절"),
    COOKING("조리중"),
    DELIVERING("배달중"),
    COMPLETED("배달 완료");

    private final String description;

}

