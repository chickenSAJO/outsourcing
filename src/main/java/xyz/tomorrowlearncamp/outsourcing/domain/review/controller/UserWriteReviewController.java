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
import xyz.tomorrowlearncamp.outsourcing.global.annotation.Auth;
import xyz.tomorrowlearncamp.outsourcing.global.entity.AuthUser;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class UserWriteReviewController {

    private final UserWriteReviewService userWriteReviewService;

    @PostMapping("")
    public ResponseEntity<Void> saveReview(
            @Auth AuthUser user,
            @Valid @RequestBody WriteReviewRequestDto requestDto
    ) {
        userWriteReviewService.saveReview(user.getId(), requestDto.getOrderId(), requestDto.getContents(), requestDto.getStar(), requestDto.getReviewUrl());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @Auth AuthUser user,
            @NotNull @Positive @RequestParam Long reviewId
    ) {

        userWriteReviewService.deleteReview(user.getId(), reviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
