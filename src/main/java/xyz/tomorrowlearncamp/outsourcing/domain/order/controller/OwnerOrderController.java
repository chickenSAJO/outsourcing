package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.common.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.domain.common.dto.AuthUser;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.OwnerOrderService;
import xyz.tomorrowlearncamp.outsourcing.global.config.aop.annotation.Order;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @Order
    @PatchMapping("/v1/orders/{orderId}/accept")
    public ResponseEntity<OrderStatusResponseDto> acceptOrder(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.acceptOrder(user.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/reject")
    public ResponseEntity<OrderStatusResponseDto> rejectOrder(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.rejectOrder(user.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/cook")
    public ResponseEntity<OrderStatusResponseDto> startCooking(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startCooking(user.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/deliver")
    public ResponseEntity<OrderStatusResponseDto> startDelivery(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startDelivery(user.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/complete")
    public ResponseEntity<OrderStatusResponseDto> completeDelivery(
            @Auth AuthUser user,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.completeDelivery(user.getId(), orderId));
    }
}
