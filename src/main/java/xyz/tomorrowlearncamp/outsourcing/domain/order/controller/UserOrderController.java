package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.global.config.aop.annotation.Order;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @Order
    @PostMapping
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @Valid @RequestBody PlaceOrderRequestDto dto
    ) {
        return ResponseEntity.ok(userOrderService.placeOrder(userId, dto));
    }

    @Order
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderStatusResponseDto> cancelOrder(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(userOrderService.cancelOrder(userId, orderId));
    }
}
