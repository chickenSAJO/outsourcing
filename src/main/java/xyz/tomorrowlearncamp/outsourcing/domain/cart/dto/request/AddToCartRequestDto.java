package xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AddToCartRequestDto {

    @NotNull(message = "메뉴는 필수 입력값입니다.")
    Long menuId;

    @Positive(message = "수량은 1개 이상이어야 합니다.")
    int quantity;
}
