package xyz.tomorrowlearncamp.outsourcing.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.request.AddToCartRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.dto.response.AddCartResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.service.UserCartService;

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
}
