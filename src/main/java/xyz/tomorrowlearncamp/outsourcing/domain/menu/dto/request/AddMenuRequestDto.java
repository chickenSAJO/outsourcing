package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddMenuRequestDto {

    @NotBlank
    private String menuName;

    @NotBlank
    private String menuContent;

    @Positive
    private int menuPrice;

    private String menuImageUrl;

    @NotBlank
    private String menuStatus;

    public AddMenuRequestDto(String menuName, String menuContent, int menuPrice, String menuImageUrl, String menuStatus) {
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
    }
}
