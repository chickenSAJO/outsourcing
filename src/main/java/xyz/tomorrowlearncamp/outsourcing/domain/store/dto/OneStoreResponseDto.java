package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;

import java.time.LocalTime;
import java.util.List;

@Getter
public class OneStoreResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minimumOrder;
    private final List<MenuEntity> menuList;

    public OneStoreResponseDto(Long storeId, String storeTitle, LocalTime openTime, LocalTime closeTime, int minimumOrder, List<MenuEntity> menuList) {
        this.storeId = storeId;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.menuList = menuList;
    }

    public static OneStoreResponseDto from(StoreEntity storeEntity, List<MenuEntity> menuList){
        return new OneStoreResponseDto(
                storeEntity.getStoreId(),
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder(),
                menuList
        );
    }
}
