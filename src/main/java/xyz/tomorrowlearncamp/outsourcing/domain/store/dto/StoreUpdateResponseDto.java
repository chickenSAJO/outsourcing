package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class StoreUpdateResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minimumOrder;
}
