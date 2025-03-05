package xyz.tomorrowlearncamp.outsourcing.domain.order.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOrderService {

    private final OrderRepository userOrderRepository;
    private final UserOrderListService userOrderListService;
    private final UserCartService userCartService;

    @Transactional
    public PlaceOrderResponseDto placeorder(Long userId, @Valid PlaceOrderRequestDto dto) {
        List<CartEntity> cartItems = userCartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new InvalidRequestException("장바구니가 비었습니다.");
        }

        // TODO : 가게 - 메뉴 연결 후 수정
//        StoreEntity store = cartItems.get(0).getMenu().getStore();
//        LocalTime now = LocalTime.now();
//        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
//            throw new InvalidRequestException("현재 영업 시간이 아닙니다.");
//        }

        // 장바구니에 담긴 메뉴의 가격을 가져와서 총주문 금액 얻기
        int totalPrice = cartItems.stream()
                .mapToInt(cart -> cart.getMenu().getMenuPrice())
                .sum();

//        if (totalPrice < store.getMinimumOrder()) {
//            throw new InvalidRequestException("최소 주문 금액 이상 주문해야 합니다.");
//        }

        UserOrderEntity order = new UserOrderEntity(totalPrice, dto.getPayment());
        userOrderRepository.save(order);

        userOrderListService.saveOrderList(order, cartItems);
        userCartService.removeAllCartItem(userId);

        return new PlaceOrderResponseDto(order.getId(), "store.getStoreTitle()", totalPrice, order.getPayment());
    }
}
