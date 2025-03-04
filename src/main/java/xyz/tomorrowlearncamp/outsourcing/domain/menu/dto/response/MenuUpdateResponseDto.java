package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;

@Getter
public class MenuUpdateResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final int menuPrice;
    private final String menuImageUrl;
    private final MenuType menuStatus;

    public MenuUpdateResponseDto(Long menuId, String menuName, String menuContent, int menuPrice, String menuImageUrl, MenuType menuStatus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
    }
}
