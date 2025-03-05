package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;

import java.time.LocalTime;

@Getter
public class SaveStoreResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minimumOrder;
    private final String userName;
    private boolean is_deleted;

    public SaveStoreResponseDto(Long storeId, String storeTitle, LocalTime openTime, LocalTime closeTime, int minimumOrder, String userName) {
        this.storeId = storeId;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.userName = userName;
        this.is_deleted = false;
    }

    public static SaveStoreResponseDto from(StoreEntity storeEntity){
        return new SaveStoreResponseDto(
                storeEntity.getStoreId(),
                storeEntity.getStoreTitle(),
                storeEntity.getOpenTime(),
                storeEntity.getCloseTime(),
                storeEntity.getMinimumOrder(),
                storeEntity.getUser().getName()
        );
    }
}
