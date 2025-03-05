package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @PostMapping
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @Valid @RequestBody PlaceOrderRequestDto dto
    ) {
        return ResponseEntity.ok(userOrderService.placeOrder(userId, dto));
    }

    @PatchMapping("/{orderId]/cancel")
    public ResponseEntity<Void> cancelOrder(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long orderId
    ) {
        userOrderService.cancelOrder(userId, orderId);
        return ResponseEntity.noContent().build();
    }
}
