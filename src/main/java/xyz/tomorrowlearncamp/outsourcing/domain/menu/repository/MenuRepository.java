package xyz.tomorrowlearncamp.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
