package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreSaveResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final String openTime;
    private final String closeTime;
    private final int minimumOrder;
    private final String userName;
}
