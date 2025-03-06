package xyz.tomorrowlearncamp.outsourcing.global.config.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.OrderStatusResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.dto.response.PlaceOrderResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.enums.OrderStatus;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OrderLoggingAspect {

    private final UserOrderService userOrderService;

    @Around("@annotation(xyz.tomorrowlearncamp.outsourcing.domain.order.annotation.Order)")
    public Object logOrderApiAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        if (!(result instanceof ResponseEntity<?> responseEntity)) {
            log.warn("Response가 ResponseEntity가 아닙니다. 실제 반환 타입: {}", result.getClass().getSimpleName());
            return result;
        }

        Object body = responseEntity.getBody();
        if (body == null) {
            log.warn("ResponseEntity의 body가 null입니다.");
            return result;
        }

        // 새로운 주문 생성 시 로그
        if (body instanceof OrderStatusResponseDto responseDto) {
            logOrder(startTime, 
                    responseDto.getId(),
                    responseDto.getStoreId(), 
                    responseDto.getStoreTitle(),
                    responseDto.getStatus());
            return result;
        }

        // 주문 상태 변경 시 로그
        if (body instanceof PlaceOrderResponseDto responseDto) {
            UserOrderEntity order = userOrderService.getOrderById(responseDto.getId());
            logOrder(startTime, 
                    responseDto.getId(), 
                    order.getStore().getStoreId(),
                    responseDto.getStoreTitle(),
                    order.getOrderStatus());
            return result;
        }

        log.warn("지원하지 않는 Response DTO 타입: {}", body.getClass().getSimpleName());
        return result;
    }

    private void logOrder(long startTime, Long orderId, Long storeId, String storeTitle, OrderStatus status) {
        log.info("[주문 로그] 요청 시각: {}, 주문 ID: {}, 가게 ID: {}, 가게명: {}, 현재 상태: {}",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime)),
                orderId,
                storeId,
                storeTitle,
                status
        );
    }
}
