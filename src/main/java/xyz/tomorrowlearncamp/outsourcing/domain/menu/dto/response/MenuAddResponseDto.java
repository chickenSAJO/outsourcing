package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;

@Getter
public class MenuAddResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final String imageUrl;
    private final int price;

    public MenuAddResponseDto(Long menuId, String menuName, String menuContent, String imageUrl, int price) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
