package xyz.tomorrowlearncamp.outsourcing.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE store Set is_deleted = true, deleted_at = NOW() WHERE id = ?")//소프트 딜리트
@SQLRestriction("is_deleted = false")//소프트 딜리트
@Table(name = "stores")
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeTitle;
    private String openTime;
    private String closeTime;
    private int minimumOrder;

    /*추가할것*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
    //메뉴 manu>>store @ManyToOne
    //주문 order>>store @ManyToOne

    public Store(String storeTitle, String openTime, String closeTime, int minimumOrder, UserEntity user) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.user = user;
    }

    public void update(String storeTitle, String openTime, String closeTime, int minimumOrder) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }
}
