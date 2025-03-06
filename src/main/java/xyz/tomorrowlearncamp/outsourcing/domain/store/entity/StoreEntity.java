package xyz.tomorrowlearncamp.outsourcing.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE store Set is_deleted = true, deleted_at = NOW() WHERE id = ?")//소프트 딜리트
@SQLRestriction("is_deleted = false")//소프트 딜리트
@Table(name = "stores")
public class StoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeTitle;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minimumOrder;

    @Column(name = "is_deleted")
    private boolean is_deleted;

    /*todo:추가할것*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
    //메뉴 manu>>store @ManyToOne
    //주문 order>>store @ManyToOne

    public StoreEntity(String storeTitle, LocalTime openTime, LocalTime closeTime, int minimumOrder, UserEntity user) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.user = user;
    }

    public void update(String storeTitle, LocalTime openTime, LocalTime closeTime, int minimumOrder) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }
}
