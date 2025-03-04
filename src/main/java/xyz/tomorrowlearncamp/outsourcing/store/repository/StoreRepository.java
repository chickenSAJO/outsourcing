package xyz.tomorrowlearncamp.outsourcing.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
