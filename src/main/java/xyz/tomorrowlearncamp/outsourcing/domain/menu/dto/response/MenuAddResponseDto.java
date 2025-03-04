package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;

@Getter
public class MenuAddResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final int menuPrice;
    private final String imageUrl;


    public MenuAddResponseDto(Long menuId, String menuName, String menuContent, int menuPrice, String imageUrl) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.imageUrl = imageUrl;
    }
}
