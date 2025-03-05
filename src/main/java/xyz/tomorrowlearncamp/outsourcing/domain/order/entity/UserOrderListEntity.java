package xyz.tomorrowlearncamp.outsourcing.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;

@Getter
@Entity
@Table(name = "order_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOrderListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private UserOrderEntity order;

    public UserOrderListEntity(MenuEntity menu, UserOrderEntity order) {
        this.menu = menu;
        this.order = order;
    }
}
