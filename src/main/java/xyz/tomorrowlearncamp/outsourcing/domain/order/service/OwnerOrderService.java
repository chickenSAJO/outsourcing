package xyz.tomorrowlearncamp.outsourcing.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.ErrorOrderMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OwnerOrderService {

    private final UserOrderService userOrderService;

    @Transactional
    public OrderStatusResponseDto acceptOrder(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!Objects.equals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.NOT_OWNER_ORDER.getMessage());
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.CANNOT_CANCEL_ORDER.getMessage());
        }

        order.updateOrderStatus(OrderStatus.ACCEPTED);

        return OrderStatusResponseDto.from(order);
    }

    @Transactional
    public OrderStatusResponseDto rejectOrder(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!Objects.equals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.NOT_OWNER_ORDER.getMessage());
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.CANNOT_CANCEL_ORDER.getMessage());
        }

        order.updateOrderStatus(OrderStatus.REJECTED);

        return OrderStatusResponseDto.from(order);
    }

    @Transactional
    public OrderStatusResponseDto startCooking(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!Objects.equals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.NOT_OWNER_ORDER.getMessage());
        }

        if (OrderStatus.DELIVERING.equals(order.getOrderStatus())
        || OrderStatus.COMPLETED.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.ALREADY_DELIVERING_OR_COMPLETED.getMessage());
        }

        if (!OrderStatus.ACCEPTED.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.MUST_BE_ACCEPTED_BEFORE_COOKING.getMessage());
        }

        order.updateOrderStatus(OrderStatus.COOKING);

        return OrderStatusResponseDto.from(order);
    }

    @Transactional
    public OrderStatusResponseDto startDelivery(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!ObjectUtils.nullSafeEquals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.NOT_OWNER_ORDER.getMessage());
        }

        if (OrderStatus.DELIVERING.equals(order.getOrderStatus())
                || OrderStatus.COMPLETED.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.ALREADY_DELIVERING_OR_COMPLETED.getMessage());
        }

        if (!OrderStatus.COOKING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.MUST_BE_COOKING_BEFORE_DELIVERY.getMessage());
        }

        order.updateOrderStatus(OrderStatus.DELIVERING);

        return OrderStatusResponseDto.from(order);
    }

    @Transactional
    public OrderStatusResponseDto completeDelivery(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!ObjectUtils.nullSafeEquals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.NOT_OWNER_ORDER.getMessage());
        }

        if (OrderStatus.COMPLETED.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.ALREADY_COMPLETED.getMessage());
        }

        if (!OrderStatus.DELIVERING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.MUST_BE_DELIVERING_BEFORE_COMPLETE.getMessage());
        }

        order.updateOrderStatus(OrderStatus.COMPLETED);

        return OrderStatusResponseDto.from(order);
    }

}
