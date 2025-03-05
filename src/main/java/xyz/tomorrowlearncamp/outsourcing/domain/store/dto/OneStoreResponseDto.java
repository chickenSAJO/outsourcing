package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;

import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OneStoreResponseDto {
    private final Long storeId;
    private final String storeTitle;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minimumOrder;
    private final List<MenuEntity> menuList;
}
