package xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response;

import lombok.Getter;

@Getter
public class PlaceOrderResponseDto {

    private final Long id;
    private final String storeTitle;
    private final int totalPrice;
    private final String payment;

    public PlaceOrderResponseDto(Long id, String storeTitle, int totalPrice, String payment) {
        this.id = id;
        this.storeTitle = storeTitle;
        this.totalPrice = totalPrice;
        this.payment = payment;
    }

}
