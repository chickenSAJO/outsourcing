package xyz.tomorrowlearncamp.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findAllByStore(StoreEntity store);
}
