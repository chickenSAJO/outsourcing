package xyz.tomorrowlearncamp.outsourcing.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.OwnerOrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<Void> acceptOrder(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        ownerOrderService.acceptOrder(ownerId, orderId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/reject")
    public ResponseEntity<Void> rejectOrder(
            @SessionAttribute(name = "LOGIN_USER") Long ownerId,
            @PathVariable Long orderId
    ) {
        ownerOrderService.rejectOrder(ownerId, orderId);
        return ResponseEntity.noContent().build();
    }
}
