package xyz.tomorrowlearncamp.outsourcing.domain.review.service;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;

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
            @NotBlank @Size(min = 10, max = 100) String contents,
            @NotBlank @Min(1) @Max(5) Integer star,
            String reviewImageUrl
    ) {
//        UserEntity user = userReadService.getUser(userId);
//        OrderEntity order = orderReadService.getOrder(orderId);

        ReviewEntity review = ReviewEntity.builder()
//                .user(user)
//                .order(order)
                .contents(contents)
                .star(star)
                .reviewImageUrl(reviewImageUrl)
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(@NotBlank @Positive Long reviewId) {
        // todo : 에러 통합하기
        if(!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException();
        }

        ReviewEntity deleteReview = reviewRepository.findById(reviewId).orElseThrow(
                RuntimeException::new
        );

        reviewRepository.delete(deleteReview);

    }
}
