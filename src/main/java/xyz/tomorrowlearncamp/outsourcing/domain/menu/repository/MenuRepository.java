package xyz.tomorrowlearncamp.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
