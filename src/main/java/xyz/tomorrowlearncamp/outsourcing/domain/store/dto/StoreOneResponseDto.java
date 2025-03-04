package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;

@Getter
public class StoreOneResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final String openTime;
    private final String closeTime;
    private final int minimumOrder;

    public StoreOneResponseDto(Long storeId, String storeTitle, String openTime, String closeTime, int minimumOrder) {
        this.storeId = storeId;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }
}
