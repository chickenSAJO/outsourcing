package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;

@Getter
public class StoreSaveResponseDto {
    private final Long id;
    private final String storeTitle;
    private final String openTime;
    private final String closeTime;
    private final int minimumOrder;
    private final String userName;

    public StoreSaveResponseDto(Long id, String storeTitle, String openTime, String closeTime, int minimumOrder, String userName) {
        this.id = id;
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.userName = userName;
    }
}
