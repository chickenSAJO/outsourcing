package xyz.tomorrowlearncamp.outsourcing.domain.review.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.request.WriteReviewRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.ReadReviewService;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.WriteReviewService;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final WriteReviewService writeReviewService;

    private final ReadReviewService readReviewService;

    @GetMapping("/v1/reviews")
    public ResponseEntity<Page<ReadReviewResponseDto>> getReviews(
            @Nullable @RequestParam Long storeId, // nullable
            @Positive @RequestParam(defaultValue = "1") int page,
            @Positive @RequestParam(defaultValue = "10") int size,
            @Positive @RequestParam(defaultValue = "1") Integer minStar, // 최소 별 점수
            @Positive @RequestParam(defaultValue = "5") Integer maxStar // 최대 별 점수
    ) {
        Page<ReadReviewResponseDto> responseDtos = readReviewService.getReviews(storeId, minStar, maxStar, page, size);

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/v1/reviews")
    public ResponseEntity<Void> saveReview(
            @Auth AuthUser user,
            @Valid @RequestBody WriteReviewRequestDto requestDto
    ) {
        writeReviewService.saveReview(user.getId(), requestDto.getOrderId(), requestDto.getContents(), requestDto.getStar(), requestDto.getReviewUrl());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @Auth AuthUser user,
            @NotNull @Positive @RequestParam Long reviewId
    ) {

        writeReviewService.deleteReview(user.getId(), reviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
