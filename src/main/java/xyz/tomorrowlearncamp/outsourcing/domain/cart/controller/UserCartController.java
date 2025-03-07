package xyz.tomorrowlearncamp.outsourcing.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.UserCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserCartController {

    private final UserCartService userCartService;

    @PostMapping("/v1/carts")
    public ResponseEntity<AddCartResponseDto> addCartItem(
            @Auth AuthUser user,
            @Valid @RequestBody AddToCartRequestDto dto
    ) {
        return ResponseEntity.ok(userCartService.addCartItem(user.getId(), dto));
    }

    @GetMapping("/v1/carts")
    public ResponseEntity<List<UserCartResponseDto>> getCart(
            @Auth AuthUser user
    ) {
        return ResponseEntity.ok(userCartService.getCart(user.getId()));
    }

    @DeleteMapping("/v1/carts/{menuId}")
    public ResponseEntity<Void> removeCartItem(
            @Auth AuthUser user,
            @PathVariable Long menuId
    ) {
        userCartService.removeCartItem(user.getId(), menuId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/carts")
    public ResponseEntity<Void> removeAllCartItem(
            @Auth AuthUser user
    ) {
        userCartService.removeAllCartItem(user.getId());
        return ResponseEntity.noContent().build();
    }
}
