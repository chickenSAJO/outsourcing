package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;

@Getter
public class MenuResponseDto {
    private final Long menuId;
    private final String menuName;
    private final String menuContent;
    private final int menuPrice;
    private final String imageUrl;
    private final MenuType menuStatus;
    private final Long storeId;


    public MenuResponseDto(Long menuId, String menuName, String menuContent, int menuPrice, String imageUrl, MenuType menuStatus, Long storeId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.imageUrl = imageUrl;
        this.menuStatus = menuStatus;
        this.storeId = storeId;
    }

    public static MenuResponseDto from(MenuEntity menu) {
        return new MenuResponseDto(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getMenuContent(),
                menu.getMenuPrice(),
                menu.getMenuImageUrl(),
                menu.getMenuStatus(),
                menu.getStore().getStoreId()
        );
    }
}
