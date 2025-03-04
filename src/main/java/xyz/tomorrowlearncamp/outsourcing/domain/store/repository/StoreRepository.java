package xyz.tomorrowlearncamp.outsourcing.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Long countByUserId(Long userId);  // 사용자의 Store 개수를 반환하는 메서드
}
