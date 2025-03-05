package xyz.tomorrowlearncamp.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    @Query("SELECT m FROM MenuEntity m WHERE m.store.storeId = :storeId")
    List<MenuEntity> findAllByStore(Long storeId);
}
