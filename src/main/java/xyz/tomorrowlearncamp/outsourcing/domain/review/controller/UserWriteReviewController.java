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
            // todo: session 값 추가하기
            @Valid @RequestBody WriteReviewRequestDto requestDto
    ) {
        userWriteReviewService.saveReview(/*Long userId, requestDto.getOrderId(),*/requestDto.getStoreId(), requestDto.getContents(), requestDto.getStar(), requestDto.getReviewUrl());


        // todo: location 값 추가하기
        // ResponseEntity.created(location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            // todo: 유저값 추가하기
            @NotNull @Positive @RequestParam Long reviewId
    ) {

        userWriteReviewService.deleteReview(/*Long userId*/ reviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
