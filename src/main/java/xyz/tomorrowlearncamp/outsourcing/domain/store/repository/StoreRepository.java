package xyz.tomorrowlearncamp.outsourcing.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.dto.response.MenuResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    Long countByUser_Id(Long userId);  // 사용자의 Store 개수를 반환하는 메서드

    boolean existsByStoreTitle(String storeTitle);

    @Query("SELECT m FROM MenuEntity m " +
            "WHERE m.store.storeId = :storeId")
    List<MenuEntity> findAllByStore(Long storeId);
}
