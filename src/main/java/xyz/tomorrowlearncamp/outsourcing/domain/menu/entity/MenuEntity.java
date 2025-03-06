package xyz.tomorrowlearncamp.outsourcing.domain.menu.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.store.entity.StoreEntity;


@Getter
@Entity
@Table(name = "menu")
@NoArgsConstructor
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;

    @Column(name = "menu_content", length = 255, nullable = false)
    private String menuContent;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;

    @Column(name = "menu_image_url")
    private String menuImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_status", length = 20)
    private MenuType menuStatus;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;


    @Builder
    public MenuEntity(String menuName, String menuContent, int menuPrice, String menuImageUrl, MenuType menuStatus, StoreEntity store) {
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
        this.store = store;
    }

    public MenuEntity(long menuId, String menuName, String menuContent, int menuPrice, String menuImageUrl, MenuType menuStatus, StoreEntity store) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
        this.store = store;
    }

    public void updateMenu(String menuName, String menuContent, int menuPrice, String imageUrl, MenuType menuStatus) {
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.menuPrice = menuPrice;
        this.menuImageUrl = imageUrl;
        this.menuStatus = menuStatus;
    }

    public void deleteMenu() {
        this.menuStatus = MenuType.DELETED;
    }
}
