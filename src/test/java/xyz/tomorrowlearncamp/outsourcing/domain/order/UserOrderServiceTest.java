package xyz.tomorrowlearncamp.outsourcing.domain.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.enums.ErrorCartMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuType;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.ErrorOrderMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderListService;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserOrderServiceTest {

    @InjectMocks
    private UserOrderService userOrderService;

    @Mock
    private Clock clock;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserCartService userCartService;

    @Mock
    private UserOrderListService userOrderListService;

    /*
      UserOrderService에서 영업 시간 조건문을 주석 처리해야 테스트 성공
     */

    @Test
    public void 주문을_성공한다() {
        // given
        Long userId = 1L;
        Long cartId = 1L;
        Long menuId = 1L;
        String payment = "KAKAOPAY";
        int quantity = 2;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        StoreEntity store = new StoreEntity("Title", LocalTime.of(1,0), LocalTime.of(23,59), 5000, user);
        MenuEntity menu = new MenuEntity(menuId,"소금빵", "맛있는 소금빵", 3500, "url", MenuType.ACTIVE, store);

        CartEntity cart = new CartEntity(user, menu, quantity);
        ReflectionTestUtils.setField(cart, "id", cartId);
        List<CartEntity> cartItems = new ArrayList<>();
        cartItems.add(cart);

        int totalPrice = menu.getMenuPrice() * quantity;
        UserOrderEntity order = new UserOrderEntity(user, store, totalPrice, payment);

        given(userService.getUserEntity(userId)).willReturn(user);
        given(userCartService.getCartItems(userId)).willReturn(cartItems);
        given(orderRepository.save(any(UserOrderEntity.class))).willReturn(order);

        // 주문 리스트 저장 및 장바구니 비우기 Mock 설정
        doNothing().when(userOrderListService).saveOrderList(any(UserOrderEntity.class), anyList());
        doNothing().when(userCartService).removeAllCartItem(userId);

        // when
        PlaceOrderResponseDto response = userOrderService.placeOrder(userId, new PlaceOrderRequestDto());

        // then
        assertNotNull(response);
    }

    @Test
    public void PENDING_상태일때_주문을_정상적으로_취소한다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        Long orderId = 1L;
        String payment = "KAKAOPAY";
        int quantity = 2;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);
        StoreEntity store = new StoreEntity("Title", LocalTime.of(6,0), LocalTime.of(23,0), 5000, user);
        MenuEntity menu = new MenuEntity(menuId,"소금빵", "맛있는 소금빵", 3500, "url", MenuType.ACTIVE, store);

        int totalPrice = menu.getMenuPrice() * quantity;
        UserOrderEntity order = new UserOrderEntity(user, store, totalPrice, payment);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        // when
        OrderStatusResponseDto response = userOrderService.cancelOrder(userId, orderId);

        // then
        assertNotNull(response);
        assertEquals(OrderStatus.CANCELED, response.getStatus());
    }

    @Test
    public void PENDING_상태가_아니면_주문_취소시_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long menuId = 1L;
        Long orderId = 1L;
        String payment = "KAKAOPAY";
        int quantity = 2;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);
        StoreEntity store = new StoreEntity("Title", LocalTime.of(1,0), LocalTime.of(23,59), 5000, user);
        MenuEntity menu = new MenuEntity(menuId,"소금빵", "맛있는 소금빵", 3500, "url", MenuType.ACTIVE, store);

        int totalPrice = menu.getMenuPrice() * quantity;
        UserOrderEntity order = new UserOrderEntity(user, store, totalPrice, payment);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        order.updateOrderStatus(OrderStatus.CANCELED);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                userOrderService.cancelOrder(userId, orderId));

        assertEquals(ErrorOrderMessage.CANNOT_CANCEL_ORDER.getMessage(), exception.getMessage());
    }

    @Test
    public void 본인이_한_주문이_아닐시_예외가_발생한다() {
        // given
        Long userId = 1L;
        Long newUserId = 2L;
        Long menuId = 1L;
        Long orderId = 1L;
        String payment = "KAKAOPAY";
        int quantity = 2;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);
        StoreEntity store = new StoreEntity("Title", LocalTime.of(1,0), LocalTime.of(23,59), 5000, user);
        MenuEntity menu = new MenuEntity(menuId,"소금빵", "맛있는 소금빵", 3500, "url", MenuType.ACTIVE, store);

        int totalPrice = menu.getMenuPrice() * quantity;
        UserOrderEntity order = new UserOrderEntity(user, store, totalPrice, payment);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                userOrderService.cancelOrder(newUserId, orderId));

        assertEquals(ErrorOrderMessage.ONLY_USER_CAN_CANCEL.getMessage(), exception.getMessage());
    }

    @Test
    public void 최소주문금액_미달시_예외가_발생한다() {
        // given
        Long userId = 1L;
        int totalPrice = 8000;
        int minimumOrder = 20000;

        UserEntity user = mock(UserEntity.class);
        StoreEntity store = mock(StoreEntity.class);
        MenuEntity menu = mock(MenuEntity.class);
        CartEntity cart = mock(CartEntity.class);

        given(userService.getUserEntity(userId)).willReturn(user);
        given(userCartService.getCartItems(userId)).willReturn(List.of(cart));

        given(cart.getMenu()).willReturn(menu);
        given(cart.getQuantity()).willReturn(1);

        given(menu.getMenuPrice()).willReturn(totalPrice);
        given(menu.getStore()).willReturn(store);

        given(store.getOpenTime()).willReturn(LocalTime.of(1,0));
        given(store.getCloseTime()).willReturn(LocalTime.of(23,59));
        given(store.getMinimumOrder()).willReturn(minimumOrder);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                userOrderService.placeOrder(userId, new PlaceOrderRequestDto()));

        assertEquals(ErrorOrderMessage.MINIMUM_ORDER_NOT_MET.getMessage(), exception.getMessage());
    }

    @Test
    public void 장바구니가_비었을_경우_예외가_발생한다() {
        // given
        Long userId = 1L;
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        given(userService.getUserEntity(userId)).willReturn(user);
        given(userCartService.getCartItems(userId)).willReturn(Collections.emptyList());

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                userOrderService.placeOrder(userId, new PlaceOrderRequestDto()));

        assertEquals(ErrorCartMessage.EMPTY_CART.getMessage(), exception.getMessage());
    }

    @Test
    void 주문_ID로_조회할_때_존재하지_않으면_예외가_발생한다() {
        // given
        Long orderId = 999L;

        given(orderRepository.findById(orderId)).willReturn(Optional.empty());

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                        userOrderService.getOrderById(orderId));

        assertEquals(ErrorOrderMessage.ORDER_NOT_FOUND.getMessage(), exception.getMessage());
    }

}
