package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;

@Getter
public class MenuResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final int price;
    private final String imageUrl;
    private final String menuStatus;


    public MenuResponseDto(Long menuId, String menuName, String menuContent, int price, String imageUrl, String menuStatus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.price = price;
        this.imageUrl = imageUrl;
        this.menuStatus = menuStatus;
    }
}
