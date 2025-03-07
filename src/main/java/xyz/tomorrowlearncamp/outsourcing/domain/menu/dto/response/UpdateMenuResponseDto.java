package xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;

@Getter
public class UpdateMenuResponseDto {
    private final Long id;
    private final String menuName;
    private final String menuContent;
    private final int menuPrice;
    private final String menuImageUrl;
    private final MenuType menuStatus;

    public UpdateMenuResponseDto(Long id, String menuName, String menuContent, int menuPrice, String menuImageUrl, MenuType menuStatus) {
        this.id = id;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
    }

    public static UpdateMenuResponseDto from(MenuEntity menu) {
        return new UpdateMenuResponseDto(
                menu.getId(),
                menu.getMenuName(),
                menu.getMenuContent(),
                menu.getMenuPrice(),
                menu.getMenuImageUrl(),
                menu.getMenuStatus()
        );
    }

}
