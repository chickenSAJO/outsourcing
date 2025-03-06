package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.OwnerOrderService;
import xyz.tomorrowlearncamp.outsourcing.global.config.aop.annotation.Order;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @Order
    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<OrderStatusResponseDto> acceptOrder(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.acceptOrder(ownerId, orderId));
    }

    @Order
    @PatchMapping("/{orderId}/reject")
    public ResponseEntity<OrderStatusResponseDto> rejectOrder(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.rejectOrder(ownerId, orderId));
    }

    @Order
    @PatchMapping("/{orderId}/cook")
    public ResponseEntity<OrderStatusResponseDto> startCooking(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startCooking(ownerId, orderId));
    }

    @Order
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<OrderStatusResponseDto> startDelivery(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.startDelivery(ownerId, orderId));
    }

    @Order
    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<OrderStatusResponseDto> completeDelivery(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ownerOrderService.completeDelivery(ownerId, orderId));
    }
}
