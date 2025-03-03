package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuAddRequestDto {

    @NotBlank(message = "메뉴명은 필수로 입력되어야 합니다.")
    private String menuName;

    @NotBlank(message = "메뉴설명은 필수로 입력되어야 합니다.")
    private String menuContent;

    @NotBlank(message = "가격은 필수로 입력되어야 합니다.")
    private int price;

    private String menuImageUrl;
    private String menuStatus;
}
