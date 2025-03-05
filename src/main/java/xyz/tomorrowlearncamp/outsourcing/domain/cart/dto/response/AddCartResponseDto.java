package xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;

@Getter
public class AddCartResponseDto {

    private final Long id;
    private final Long menuId; // 임시 필드
//    private final String storeName;
//    private final String menuName;
//    private final int menuPrice;
//    private final String menuImageUrl;
    private final int quantity;

    public AddCartResponseDto(Long id, Long menuId, int quantity) { // 임시 생성자
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static AddCartResponseDto from(CartEntity cart) {
        return new AddCartResponseDto(
                cart.getId(),
                cart.getMenuId(),
                cart.getQuantity()
        );
    }

//    public AddCartResponseDto(Long id, String storeName, String menuName, int menuPrice, String menuImageUrl, int quantity) {
//        this.id = id;
//        this.storeName = storeName;
//        this.menuName = menuName;
//        this.menuPrice = menuPrice;
//        this.menuImageUrl = menuImageUrl;
//        this.quantity = quantity;
//    }

//    public static AddCartResponseDto from(CartEntity cart) {
//        return new AddCartResponseDto(
//                cart.getId(),
//                cart.getOrder().getStore().getName(),
//                cart.getMenu().getName(),
//                cart.getMenu().getPrice(),
//                cart.getMenu().getImageUrl(),
//                cart.getQuantity()
//        );
//    }
}
