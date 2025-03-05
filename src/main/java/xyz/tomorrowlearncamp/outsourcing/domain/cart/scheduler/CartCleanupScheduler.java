package xyz.tomorrowlearncamp.outsourcing.domain.cart.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.cart.repository.CartRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartCleanupScheduler {

    private final CartRepository cartRepository;

    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시 실행
//    @Scheduled(cron = "0 */1 * * * ?") // 테스트용(매 1분마다 실행)
    @Transactional
    public void removeAllCartItemScheduler() {
        log.info("==장바구니 삭제 스케줄러 작동 시작==");

        LocalDateTime latestCartUpdatedAt = cartRepository.findLatestCartUpdatedAt();
        if (latestCartUpdatedAt != null
                && latestCartUpdatedAt.isBefore(LocalDateTime.now().minusDays(1))) {
            cartRepository.deleteAll();
        }

        log.info("==장바구니 삭제 스케줄러 작동 완료==");
    }

}
