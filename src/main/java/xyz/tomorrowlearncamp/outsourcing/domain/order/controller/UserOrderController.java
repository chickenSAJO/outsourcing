package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.domain.order.annotation.Order;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @Order
    @PostMapping
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(
            @Auth AuthUser user,
            @Valid @RequestBody PlaceOrderRequestDto dto
    ) {
        return ResponseEntity.ok(userOrderService.placeOrder(user.getId(), dto));
    }

    @Order
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderStatusResponseDto> cancelOrder(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(userOrderService.cancelOrder(user.getId(), orderId));
    }
}
