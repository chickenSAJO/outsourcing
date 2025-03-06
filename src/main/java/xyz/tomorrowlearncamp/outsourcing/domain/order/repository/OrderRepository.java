package xyz.tomorrowlearncamp.outsourcing.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;

public interface OrderRepository extends JpaRepository<UserOrderEntity, Long> {
}
