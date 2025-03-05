package xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PlaceOrderRequestDto {

    // TODO : 삭제 예정
//    @NotNull(message = "총 금액은 필수값입니다.")
//    private int totalPrice;

    @NotBlank
    private String payment;

}
