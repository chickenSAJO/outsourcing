package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMenuRequestDto {
    private String menuName;
    private String menuContent;
    private int menuPrice;
    private String menuImageUrl;
    private String menuStatus;
}
