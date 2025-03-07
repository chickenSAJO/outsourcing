package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.OwnerOrderService;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.domain.order.annotation.Order;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @Order
    @PatchMapping("/v1/orders/{orderId}/accept")
    public ResponseEntity<OrderStatusResponseDto> acceptOrder(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.acceptOrder(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/reject")
    public ResponseEntity<OrderStatusResponseDto> rejectOrder(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.rejectOrder(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/cook")
    public ResponseEntity<OrderStatusResponseDto> startCooking(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startCooking(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/deliver")
    public ResponseEntity<OrderStatusResponseDto> startDelivery(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startDelivery(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/v1/orders/{orderId}/complete")
    public ResponseEntity<OrderStatusResponseDto> completeDelivery(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.completeDelivery(owner.getId(), orderId));
    }
}
