package xyz.tomorrowlearncamp.outsourcing.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.UserCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.enums.ErrorCartMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.repository.CartRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.entity.MenuEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.enums.ErrorMenuMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;

    @Transactional
    public AddCartResponseDto addCartItem(
            Long userId,
            AddToCartRequestDto dto
    ) {
        UserEntity user = userService.getUserEntity(userId);

        MenuEntity menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new InvalidRequestException(ErrorMenuMessage.NOT_FOUND_MENU.getErrorMessage()));

        CartEntity cart = cartRepository.findByUserIdAndMenuId(userId, dto.getMenuId())
                .map(existingCart -> {
                    existingCart.updateQuantity(dto.getQuantity());
                    return existingCart;
                })
                .orElseGet(() -> cartRepository.save(new CartEntity(user, menu, dto.getQuantity())));

        return AddCartResponseDto.from(cart);
    }

    public List<UserCartResponseDto> getCart(Long userId) {
        List<CartEntity> cartItems = cartRepository.findByUserId(userId);

        return cartItems.stream()
                .map(UserCartResponseDto::from).toList();
    }

    @Transactional
    public void removeCartItem(Long userId, Long menuId) {
        CartEntity cart = cartRepository.findByUserIdAndMenuId(userId, menuId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCartMessage.CART_ITEM_NOT_FOUND.getMessage()));
        cartRepository.delete(cart);
    }

    @Transactional
    public void removeAllCartItem(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    public List<CartEntity> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
