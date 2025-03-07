package xyz.tomorrowlearncamp.outsourcing.domain.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.OwnerOrderService;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OwnerOrderServiceTest {

    @InjectMocks
    private OwnerOrderService ownerOrderService;

    @Mock
    private UserOrderService userOrderService;

    @Test
    public void 주문을_정상적으로_수락한다() {
        // given
        Long ownerId = 1L;
        Long orderId = 1L;

        UserEntity owner = new UserEntity();
        ReflectionTestUtils.setField(owner, "id", ownerId);

        StoreEntity store = new StoreEntity("Test Store", LocalTime.of(6, 0), LocalTime.of(23, 0), 5000, owner);

        UserOrderEntity order = new UserOrderEntity(owner, store, 10000, "KAKAOPAY");
        ReflectionTestUtils.setField(order, "id", orderId);

        given(userOrderService.getOrderById(orderId)).willReturn(order);

        // when
        OrderStatusResponseDto response = ownerOrderService.acceptOrder(ownerId, orderId);

        // then
        assertNotNull(response);
        assertEquals(OrderStatus.ACCEPTED, response.getStatus());
    }

}
