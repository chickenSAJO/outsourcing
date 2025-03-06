package xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;

@Getter
public class OrderStatusResponseDto {
    private final Long id;
    private final Long storeId;
    private final String storeTitle;
    private final OrderStatus status;

    public OrderStatusResponseDto(Long id, Long storeId, String storeTitle, OrderStatus status) {
        this.id = id;
        this.storeId = storeId;
        this.storeTitle = storeTitle;
        this.status = status;
    }

    public static OrderStatusResponseDto from(UserOrderEntity order) {
        return new OrderStatusResponseDto(
                order.getId(),
                order.getStore().getStoreId(),
                order.getStore().getStoreTitle(),
                order.getOrderStatus()
        );
    }
}
