package xyz.tomorrowlearncamp.outsourcing.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.UserCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;
import xyz.tomorrowlearncamp.outsourcing.global.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.global.entity.AuthUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class UserCartController {

    private final UserCartService userCartService;

    @PostMapping
    public ResponseEntity<AddCartResponseDto> addCartItem(
            @Auth AuthUser user,
            @Valid @RequestBody AddToCartRequestDto dto
    ) {
        return ResponseEntity.ok(userCartService.addCartItem(user.getId(), dto));
    }

    @GetMapping
    public ResponseEntity<List<UserCartResponseDto>> getCart(
            @Auth AuthUser user
    ) {
        return ResponseEntity.ok(userCartService.getCart(user.getId()));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> removeCartItem(
            @Auth AuthUser user,
            @PathVariable Long menuId
    ) {
        userCartService.removeCartItem(user.getId(), menuId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllCartItem(
            @Auth AuthUser user
    ) {
        userCartService.removeAllCartItem(user.getId());
        return ResponseEntity.noContent().build();
    }
}
