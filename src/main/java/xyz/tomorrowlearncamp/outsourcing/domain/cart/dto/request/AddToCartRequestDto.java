package xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddToCartRequestDto {

    @NotBlank
    Long menuId;

    @NotBlank
    int quantity;
}
