package xyz.tomorrowlearncamp.outsourcing.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerOrderService {

    private final UserOrderService userOrderService;
    private final OrderRepository orderRepository;

    public void acceptOrder(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!ownerId.equals(order.getStore().getUser().getId())) {
            throw new InvalidRequestException("본인의 가게 주문만 수락할 수 있습니다.");
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException("주문을 취소할 수 없습니다.");
        }

        order.updateOrderStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order);
    }

    public void rejectOrder(Long ownerId, Long orderId) {
        UserOrderEntity order = userOrderService.getOrderById(orderId);

        if (!ownerId.equals(order.getStore().getUser().getId())) {
            throw new InvalidRequestException("본인의 가게 주문만 거절할 수 있습니다.");
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException("주문을 취소할 수 없습니다.");
        }

        order.updateOrderStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }
}
