package xyz.tomorrowlearncamp.outsourcing.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserIdAndMenuId(Long userId, Long menuId);
    List<CartEntity> findByUserId(Long userId);

}
