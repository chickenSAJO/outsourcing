package xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PlaceOrderRequestDto {

    @NotBlank
    private String payment;

}
