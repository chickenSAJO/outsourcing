package xyz.tomorrowlearncamp.outsourcing.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE store Set is_deleted = true, deleted_at = NOW() WHERE id = ?")//소프트 딜리트
@SQLRestriction("is_deleted = false")//소프트 딜리트
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeTitle;
    private String openTime;
    private String closeTime;
    private int minimumOrder;

    /*추가할것*/
    private Long userId;
    //유저 아이디 @OneToMany
    //창업일 //상속
    //수정일 //상속

    public Store(String storeTitle, String openTime, String closeTime, int minimumOrder, Long userId) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.userId = userId;
    }

    public void update(String storeTitle, String openTime, String closeTime, int minimumOrder) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }
}
