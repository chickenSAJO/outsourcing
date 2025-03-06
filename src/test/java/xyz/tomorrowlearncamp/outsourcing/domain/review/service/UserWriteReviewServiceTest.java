package xyz.tomorrowlearncamp.outsourcing.domain.review.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.enums.ErrorReviewMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;
import xyz.tomorrowlearncamp.outsourcing.global.exception.UnauthorizedRequestException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserWriteReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserOrderService orderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserWriteReviewService userReviewService;

    @Test
    void review_저장() {
        //given
        Long userId = 1L;
        Long orderId = 1L;
        String contents = "contents";
        Integer star = 5;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        UserOrderEntity orderEntity = new UserOrderEntity();
        ReflectionTestUtils.setField(orderEntity, "id", orderId);

        given(userService.getUserEntity(anyLong())).willReturn(user);
        given(orderService.getOrderById(anyLong())).willReturn(orderEntity);

        // when
        userReviewService.saveReview(userId, orderId, contents, star, null);

        // then
        verify(reviewRepository, times(1)).save(any(ReviewEntity.class));
    }

    @Test
    void 리뷰_삭제_실패_없는리뷰() {
        //given
        given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());

        // when && then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> userReviewService.deleteReview(1L, 1L));

        // then
        assertEquals(ErrorReviewMessage.NOT_FOUND_REVIEW.getErrorMessage(), exception.getMessage());
    }

    @Test
    void 리뷰_삭제_실패_다른사용자() {
        //given
        // 리뷰 랑 같은 사용자 그래서 같은 아이디을 사용
        Long reviewId = 1L;

        // 다른 사용자
        Long userId = 2L;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", reviewId);

        ReviewEntity review = new ReviewEntity();
        ReflectionTestUtils.setField(review, "id", reviewId);
        ReflectionTestUtils.setField(review, "user", user);

        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        // when && then
        UnauthorizedRequestException exception = assertThrows(UnauthorizedRequestException.class,
                () -> userReviewService.deleteReview(userId, reviewId));

        // then
        assertEquals(ErrorReviewMessage.UNAUTHORIZED.getErrorMessage(), exception.getMessage());
    }

    @Test
    void 리뷰_삭제_성공() {
        //given
        Long reviewId = 1L;

        Long userId = 1L;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        ReviewEntity review = new ReviewEntity();
        ReflectionTestUtils.setField(review, "id", reviewId);
        ReflectionTestUtils.setField(review, "user", user);

        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));
        doNothing().when(reviewRepository).delete(any(ReviewEntity.class));

        // when
        userReviewService.deleteReview(userId, reviewId);

        // then
        verify(reviewRepository, times(1)).delete(any(ReviewEntity.class));
    }
}