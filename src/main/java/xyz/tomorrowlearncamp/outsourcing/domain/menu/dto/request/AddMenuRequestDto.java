package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddMenuRequestDto {

    @NotNull
    private String menuName;

    @NotNull
    private String menuContent;

    @NotNull
    private int menuPrice;

    private String menuImageUrl;

    @NotNull
    private String menuStatus;
}
