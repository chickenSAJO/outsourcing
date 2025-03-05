package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;

@Getter

public class AddMenuResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final int menuPrice;
    private final String imageUrl;
    private final MenuType menuStatus;

    public AddMenuResponseDto(Long menuId, String menuName, String menuContent, int menuPrice, String imageUrl, MenuType menuStatus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.imageUrl = imageUrl;
        this.menuStatus = menuStatus;
    }

    public static AddMenuResponseDto from(MenuEntity menu) {
        return new AddMenuResponseDto(
                menu.getId(),
                menu.getMenuName(),
                menu.getMenuContent(),
                menu.getMenuPrice(),
                menu.getMenuImageUrl(),
                menu.getMenuStatus()
        );
    }
}
