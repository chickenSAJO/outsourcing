package xyz.tomorrowlearncamp.outsourcing.domain.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @Column(nullable = false)
    private Integer quantity;

    public CartEntity(UserEntity user,MenuEntity menu, int quantity) {
        this.user = user;
        this.menu = menu;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }
}
