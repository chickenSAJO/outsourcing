package xyz.tomorrowlearncamp.outsourcing.domain.review.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.UserReadReviewService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class UserReadReviewController {

    private final UserReadReviewService userReadReviewService;

    @GetMapping("")
    public ResponseEntity<Page<ReadReviewResponseDto>> getReviews(
            @Nullable @RequestParam Long storeId, // nullable
            @Positive @RequestParam(defaultValue = "1") int page,
            @Positive @RequestParam(defaultValue = "10") int size,
            @Positive @RequestParam(defaultValue = "1") Integer minStar, // 최소 별 점수
            @Positive @RequestParam(defaultValue = "5") Integer maxStar // 최대 별 점수
    ) {
        Page<ReadReviewResponseDto> responseDtos = userReadReviewService.getReviews(storeId, minStar, maxStar, page, size);

        return ResponseEntity.ok(responseDtos);
    }

}
