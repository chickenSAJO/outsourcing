package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuAddRequestDto {

    private String menuName;
    private String menuContent;
    private int price;
    private String menuImageUrl;
    private String menuStatus;
}
