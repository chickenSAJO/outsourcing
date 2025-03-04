package xyz.tomorrowlearncamp.outsourcing.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.UserCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class UserCartController {

    private final UserCartService userCartService;

    @PostMapping
    public ResponseEntity<AddCartResponseDto> addCartItem(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @RequestBody AddToCartRequestDto dto
    ) {
        return ResponseEntity.ok(userCartService.addCartItem(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<UserCartResponseDto>> getCart(
            @SessionAttribute(name = "LOGIN_USER") Long userId
    ) {
        return ResponseEntity.ok(userCartService.getCart(userId));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> removeCartItem(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long menuId
    ) {
        userCartService.removeCartItem(userId, menuId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllCartItem(
            @SessionAttribute(name = "LOGIN_USER") Long userId
    ) {
        userCartService.removeAllCartItem(userId);
        return ResponseEntity.noContent().build();
    }
}
