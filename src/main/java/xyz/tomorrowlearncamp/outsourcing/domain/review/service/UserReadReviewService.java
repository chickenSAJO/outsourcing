package xyz.tomorrowlearncamp.outsourcing.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.enums.ErrorReviewMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReadReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewEntity findReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new InvalidRequestException(ErrorReviewMessage.NOT_FOUND_REVIEW.getErrorMessage())
        );
    }

    public Page<ReadReviewResponseDto> getReviews(@Nullable Long storeId, Integer minStar, Integer maxStar, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<ReadReviewResponseDto> result;

        if (storeId != null) {
            result = reviewRepository.findAllByStore_Id(storeId, pageable); // 가게로 조회
        } else {
            result = reviewRepository.findAllByStar(minStar, maxStar, pageable); // 별점으로 조회
        }

        return result;
    }
}
