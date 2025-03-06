package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.OwnerOrderService;
import xyz.tomorrowlearncamp.outsourcing.global.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.global.annotation.Order;
import xyz.tomorrowlearncamp.outsourcing.global.entity.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @Order
    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<OrderStatusResponseDto> acceptOrder(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.acceptOrder(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/{orderId}/reject")
    public ResponseEntity<OrderStatusResponseDto> rejectOrder(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.rejectOrder(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/{orderId}/cook")
    public ResponseEntity<OrderStatusResponseDto> startCooking(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startCooking(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<OrderStatusResponseDto> startDelivery(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startDelivery(owner.getId(), orderId));
    }

    @Order
    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<OrderStatusResponseDto> completeDelivery(
            @Auth AuthUser owner,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.completeDelivery(owner.getId(), orderId));
    }
}
