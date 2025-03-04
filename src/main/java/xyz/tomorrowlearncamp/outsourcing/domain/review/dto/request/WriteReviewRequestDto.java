package xyz.tomorrowlearncamp.outsourcing.domain.review.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WriteReviewRequestDto {

//    @NotBlank
//    @Positive
//    private Long orderId;

    @NotNull
    @Positive
    private Long storeId;

    @NotNull
    @Size(min = 10, max = 100)
    private String contents;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer star;

    private String reviewUrl;

    public WriteReviewRequestDto(/*Long orderId,*/Long storeId, String contents, Integer star, @Nullable String reviewUrl) {
//        this.orderId = orderId;
        this.storeId = storeId;
        this.star = star;
        this.contents = contents;
        this.reviewUrl = reviewUrl;
    }
}
