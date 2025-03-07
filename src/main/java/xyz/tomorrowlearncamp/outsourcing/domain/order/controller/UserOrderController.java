package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.request.PlaceOrderRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.domain.order.annotation.Order;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @Order
    @PostMapping("/v1/orders")
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(
            @Auth AuthUser user,
            @Valid @RequestBody PlaceOrderRequestDto dto
    ) {
        return ResponseEntity.ok(userOrderService.placeOrder(user.getId(), dto));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/cancel")
    public ResponseEntity<OrderStatusResponseDto> cancelOrder(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(userOrderService.cancelOrder(user.getId(), orderId));
    }
}
