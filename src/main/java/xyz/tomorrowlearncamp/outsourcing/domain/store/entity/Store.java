package xyz.tomorrowlearncamp.outsourcing.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.User;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE store Set is_deleted = true, deleted_at = NOW() WHERE id = ?")//소프트 딜리트
@SQLRestriction("is_deleted = false")//소프트 딜리트
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeTitle;
    private String openTime;
    private String closeTime;
    private int minimumOrder;

    /*추가할것*/
    //유저 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    //메뉴 manu>>store @ManyToOne
    //리뷰 Review>>store @ManyToOne

    public Store(String storeTitle, String openTime, String closeTime, int minimumOrder, User user) {
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
