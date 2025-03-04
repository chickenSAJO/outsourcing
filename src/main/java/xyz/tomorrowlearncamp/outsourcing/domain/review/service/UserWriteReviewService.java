package xyz.tomorrowlearncamp.outsourcing.domain.review.service;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.enums.ReviewErrorMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
@Validated
public class UserWriteReviewService {

    private final ReviewRepository reviewRepository;

//    private final UserReadService userReadService;

    @Transactional
    public void saveReview(
            /*@NotBlack @Positive Long userId,
            @NotBlank @Positive Long orderId,*/
            @NotNull @Positive Long storeId,
            @NotNull @Size(min = 10, max = 100) String contents,
            @NotNull @Min(1) @Max(5) Integer star,
            String reviewImageUrl
    ) {
//        UserEntity user = userReadService.getUser(userId);
//        OrderEntity order = orderReadService.getOrder(orderId);

        ReviewEntity review = ReviewEntity.builder()
//                .user(user)
//                .order(order)
                .storeId(storeId)
                .contents(contents)
                .star(star)
                .reviewImageUrl(reviewImageUrl)
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(@NotBlank @Positive Long reviewId) {
        ReviewEntity deleteReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new InvalidRequestException(ReviewErrorMessage.NOT_FOUND_REVIEW.getErrorMessage())
        );

        reviewRepository.delete(deleteReview);

    }
}
