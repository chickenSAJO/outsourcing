package xyz.tomorrowlearncamp.outsourcing.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderListEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.repository.OrderListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOrderListService {

    private final OrderListRepository orderListRepository;

    @Transactional
    public void saveOrderList(UserOrderEntity order, List<CartEntity> cartItems) {
        List<UserOrderListEntity> orderLists = cartItems.stream()
                .map(cart -> new UserOrderListEntity(cart.getMenu(), order))
                .toList();
        orderListRepository.saveAll(orderLists);
    }

}
