package xyz.tomorrowlearncamp.outsourcing.domain.review.service;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.service.OwnerWriteCommentService;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.order.service.UserOrderService;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.enums.ErrorReviewMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;
import xyz.tomorrowlearncamp.outsourcing.global.exception.UnauthorizedRequestException;

@Service
@RequiredArgsConstructor
@Validated
public class UserWriteReviewService {

    private final ReviewRepository reviewRepository;

    private final UserOrderService orderService;

    private final UserService userService;

    @Transactional
    public void saveReview(
            Long userId,
            /*@NotBlank @Positive Long orderId,*/
            @NotNull @Positive Long orderId,
            @NotNull @Size(min = 10, max = 100) String contents,
            @NotNull @Min(1) @Max(5) Integer star,
            String reviewImageUrl
    ) {
        UserEntity user = userService.getUserEntity(userId);
        UserOrderEntity order = orderService.getOrderById(orderId);

        ReviewEntity review = ReviewEntity.builder()
                .user(user)
                .orderEntity(order)
                .contents(contents)
                .star(star)
                .reviewImageUrl(reviewImageUrl)
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long userId, @NotNull @Positive Long reviewId) {
        ReviewEntity deleteReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new InvalidRequestException(ErrorReviewMessage.NOT_FOUND_REVIEW.getErrorMessage())
        );

        if (ObjectUtils.nullSafeEquals(deleteReview.getUser().getId(), userId)) {
            throw new UnauthorizedRequestException(ErrorReviewMessage.UNAUTHORIZED.getErrorMessage());
        }

        reviewRepository.delete(deleteReview);

    }
}
