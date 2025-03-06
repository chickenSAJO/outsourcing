package xyz.tomorrowlearncamp.outsourcing.domain.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderListRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderListService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserOrderListServiceTest {

    @InjectMocks
    private UserOrderListService userOrderListService;

    @Mock
    private OrderListRepository orderListRepository;

    @Test
    public void 주문_상세_리스트가_정상적으로_저장된다() {
        // given
        UserOrderEntity order = new UserOrderEntity();
        UserEntity user = new UserEntity();
        MenuEntity menu1 = new MenuEntity();
        MenuEntity menu2 = new MenuEntity();
        CartEntity cart1 = new CartEntity(user, menu1, 2);
        CartEntity cart2 = new CartEntity(user, menu2, 1);
        List<CartEntity> cartItems = List.of(cart1, cart2);

        // When
        userOrderListService.saveOrderList(order, cartItems);

        // Then
        verify(orderListRepository, times(1)).saveAll(anyList());
    }
}
