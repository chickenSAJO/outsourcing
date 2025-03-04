package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;

@Getter
public class StoreResponseDto {
    private final Long id;
    private final String storeTitle;
    private final String openTime;
    private final String closeTime;
    private final int minimumOrder;

    public StoreResponseDto(Long id, String storeTitle, String openTime, String closeTime, int minimumOrder) {
        this.id = id;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }
}
