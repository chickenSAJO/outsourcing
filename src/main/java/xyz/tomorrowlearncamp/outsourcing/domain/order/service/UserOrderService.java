package xyz.tomorrowlearncamp.outsourcing.domain.order.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.enums.ErrorCartMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.ErrorOrderMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final OrderRepository orderRepository;
    private final UserOrderListService userOrderListService;
    private final UserCartService userCartService;
    private final UserService userService;

    @Transactional
    public PlaceOrderResponseDto placeOrder(Long userId, @Valid PlaceOrderRequestDto dto) {

        UserEntity user = userService.getUserEntity(userId);
        List<CartEntity> cartItems = userCartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new InvalidRequestException(ErrorCartMessage.EMPTY_CART.getMessage());
        }

        // 가게 영업시간에만 주문 가능
        StoreEntity store = cartItems.get(0).getMenu().getStore();
        LocalTime now = LocalTime.now();
        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new InvalidRequestException(ErrorOrderMessage.NOT_BUSINESS_HOURS.getMessage());
        }

        // 장바구니에 담긴 메뉴의 가격을 가져와서 총주문 금액 얻기
        int totalPrice = cartItems.stream()
                .mapToInt(cart -> cart.getMenu().getMenuPrice() * cart.getQuantity())
                .sum();
        if (totalPrice < store.getMinimumOrder()) {
            throw new InvalidRequestException(ErrorOrderMessage.MINIMUM_ORDER_NOT_MET.getMessage());
        }

        UserOrderEntity order = new UserOrderEntity(user, store, totalPrice, dto.getPayment());
        orderRepository.save(order);

        userOrderListService.saveOrderList(order, cartItems);
        userCartService.removeAllCartItem(userId);

        return new PlaceOrderResponseDto(order.getId(), store.getStoreTitle(), totalPrice, order.getPayment());
    }

    @Transactional
    public OrderStatusResponseDto cancelOrder(Long userId, Long orderId) {
        UserOrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidRequestException(ErrorOrderMessage.ORDER_NOT_FOUND.getMessage()));

        if (!Objects.equals(userId, order.getUser().getId())) {
            throw new InvalidRequestException(ErrorOrderMessage.ONLY_USER_CAN_CANCEL.getMessage());
        }

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidRequestException(ErrorOrderMessage.CANNOT_CANCEL_ORDER.getMessage());
        }

        order.updateOrderStatus(OrderStatus.CANCELED);

        return OrderStatusResponseDto.from(order);
    }

    public UserOrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidRequestException(ErrorOrderMessage.ORDER_NOT_FOUND.getMessage()));
    }
}
