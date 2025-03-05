package xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;

@Getter
public class UserCartResponseDto {
    private final Long id;
//    private final String storeTitle;
//    private final String menuName;
//    private final int menuPrice;
//    private final String menuImageUrl;
    private final int quantity;

    public UserCartResponseDto(Long id, int quantity) { // 임시 생성자
        this.id = id;
        this.quantity = quantity;
    }

//    public UserCartResponseDto(Long id, String storeTitle, String menuName, int menuPrice, String menuImageUrl, int quantity) {
//        this.id = id;
//        this.storeTitle = storeTitle;
//        this.menuName = menuName;
//        this.menuPrice = menuPrice;
//        this.menuImageUrl = menuImageUrl;
//        this.quantity = quantity;
//    }

    public static UserCartResponseDto from(CartEntity cart) {
        return new UserCartResponseDto(
                cart.getId(),
//                cart.getOrder().getStore().getName(),
//                cart.getMenu().getName(),
//                cart.getMenu().getPrice(),
//                cart.getMenu().getImageUrl(),
                cart.getQuantity()
        );
    }
}
