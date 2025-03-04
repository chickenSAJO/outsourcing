package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;

@Getter
public class MenuUpdateResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final int price;
    private final String menuImageUrl;
    private final String menuStatus;

    public MenuUpdateResponseDto(Long menuId, String menuName, String menuContent, int price, String menuImageUrl, String menuStatus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.price = price;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
    }
}
