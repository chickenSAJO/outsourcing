package xyz.tomorrowlearncamp.outsourcing.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Getter
@Entity
@Table(name = "order_entity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String payment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public UserOrderEntity(UserEntity user, StoreEntity store,  int totalPrice, String payment) {
        this.user = user;
        this.store = store;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.orderStatus = OrderStatus.PENDING; // 기본값 설정
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        if (this.orderStatus == OrderStatus.CANCELED) {
            throw new InvalidRequestException("취소된 주문은 상태를 변경할 수 없습니다.");
        }
        this.orderStatus = orderStatus;
    }
}
