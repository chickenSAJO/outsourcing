package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;

import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class StoreResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minimumOrder;

    public static StoreResponseDto from(StoreEntity storeEntity){
        return new StoreResponseDto(
                storeEntity.getStoreId(),
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder()
        );
    }
}
