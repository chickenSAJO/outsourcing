package xyz.tomorrowlearncamp.outsourcing.domain.review.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.request.WriteReviewRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.UserWriteReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class UserWriteReviewController {

    private final UserWriteReviewService userWriteReviewService;

    @PostMapping("")
    public ResponseEntity<Void> saveReview(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @Valid @RequestBody WriteReviewRequestDto requestDto
    ) {
        userWriteReviewService.saveReview(userId, requestDto.getOrderId(), requestDto.getContents(), requestDto.getStar(), requestDto.getReviewUrl());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @NotNull @Positive @RequestParam Long reviewId
    ) {

        userWriteReviewService.deleteReview(userId, reviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
