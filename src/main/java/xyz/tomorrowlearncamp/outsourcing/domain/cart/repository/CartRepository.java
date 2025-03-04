package xyz.tomorrowlearncamp.outsourcing.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserIdAndMenuId(Long userId, Long menuId);
    List<CartEntity> findByUserId(Long userId);
    void deleteByUserId(Long userId);

    @Query("SELECT MAX(c.updatedAt) FROM CartEntity c")
    LocalDateTime findLatestCartUpdatedAt();
}
