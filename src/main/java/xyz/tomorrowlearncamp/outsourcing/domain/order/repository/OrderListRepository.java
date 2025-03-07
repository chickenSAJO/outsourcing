package xyz.tomorrowlearncamp.outsourcing.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderListEntity;

public interface OrderListRepository extends JpaRepository<UserOrderListEntity, Long> {
}
