package xyz.tomorrowlearncamp.outsourcing.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.entity.CartEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.repository.CartRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddCartResponseDto addCartItem(
            Long userId,
            AddToCartRequestDto dto
    ) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        CartEntity cart = cartRepository.findByUserIdAndMenuId(userId, dto.getMenuId())
                .map(existingCart -> {
                    existingCart.updateQuantity(dto.getQuantity());
                    return existingCart;
                })
                .orElseGet(() -> cartRepository.save(new CartEntity(user, dto.getMenuId(), dto.getQuantity())));

        return AddCartResponseDto.from(cart);
    }
}
