package xyz.tomorrowlearncamp.outsourcing.domain.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "MENU")
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "STORE_ID", nullable = false)
//    private Store store;

    @Column(name = "MENU_NAME", length = 100, nullable = false)
    private String menuName;

    @Column(name = "MENU_CONTENT", length = 255, nullable = false)
    private String menuContent;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @Column(name = "MENU_IMAGE_URL")
    private String menuImageUrl;

    @Column(name = "STATUS", length = 20)
    private String menuStatus;


    public Menu(String menuName, String menuContent, int price, String menuImageUrl, String menuStatus) {
//        this.store = store;
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.price = price;
        this.menuImageUrl = menuImageUrl;
        this.menuStatus = menuStatus;
    }

    public void update(String menuName, String menuContent, int price, String imageUrl, String menuStatus) {
        this.menuName = menuName;
        this.menuContent = menuContent;
        this.price = price;
        this.menuImageUrl = imageUrl;
        this.menuStatus = menuStatus;
    }
}
