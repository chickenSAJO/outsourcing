package xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;

@Getter
public class AddCartResponseDto {

    private final Long id;
//    private final String storeName;
    private final String menuName;
    private final int menuPrice;
    private final String menuImageUrl;
    private final int quantity;

    public AddCartResponseDto(Long id/* ,String storeName */, String menuName, int menuPrice, String menuImageUrl, int quantity) {
        this.id = id;
//        this.storeName = storeName;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImageUrl = menuImageUrl;
        this.quantity = quantity;
    }

    public static AddCartResponseDto from(CartEntity cart) {
        return new AddCartResponseDto(
                cart.getId(),
//                cart.getMenu().getStore().getName(),
                cart.getMenu().getMenuName(),
                cart.getMenu().getMenuPrice(),
                cart.getMenu().getMenuImageUrl(),
                cart.getQuantity()
        );
    }
}
