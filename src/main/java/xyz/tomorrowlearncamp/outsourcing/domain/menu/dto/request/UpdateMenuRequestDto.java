package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMenuRequestDto {

    @NotNull
    @NotBlank
    private String menuName;

    @NotNull
    @NotBlank
    private String menuContent;

    @NotNull
    @Positive
    private int menuPrice;

    private String menuImageUrl;

    @NotNull
    @NotBlank
    private String menuStatus;
}
