package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;

import java.time.LocalTime;

@Getter
public class UpdateStoreResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minimumOrder;

    public UpdateStoreResponseDto(Long storeId, String storeTitle, LocalTime openTime, LocalTime closeTime, int minimumOrder) {
        this.storeId = storeId;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }

    public static UpdateStoreResponseDto from(StoreEntity storeEntity){
        return new UpdateStoreResponseDto(
                storeEntity.getStoreId(),
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder()
        );
    }
}
