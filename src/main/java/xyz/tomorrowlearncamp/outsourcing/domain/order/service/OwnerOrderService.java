package xyz.tomorrowlearncamp.outsourcing.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.ErrorOrderMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OwnerOrderService {

    private final UserOrderService userOrderService;
    private final OrderRepository orderRepository;

    @Transactional
    public void acceptOrder(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!Objects.equals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.ONLY_OWNER_CAN_ACCEPT.getMessage());
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.CANNOT_CANCEL_ORDER.getMessage());
        }

        order.updateOrderStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order);
    }

    @Transactional
    public void rejectOrder(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!Objects.equals(ownerId, order.getStore().getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.ONLY_OWNER_CAN_REJECT.getMessage());
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.CANNOT_CANCEL_ORDER.getMessage());
        }

        order.updateOrderStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }
}
