package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;

@Getter
public class StoreSaveResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final String openTime;
    private final String closeTime;
    private final int minimumOrder;
    private final String userName;

    public StoreSaveResponseDto(Long storeId, String storeTitle, String openTime, String closeTime, int minimumOrder, String userName) {
        this.storeId = storeId;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.userName = userName;
    }
}
