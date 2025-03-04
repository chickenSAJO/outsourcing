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

    @NotBlank
    @Size(min = 10, max = 100)
    private String contents;

    @NotBlank
    @Min(1)
    @Max(5)
    private Integer star;

    private String reviewUrl;

    public WriteReviewRequestDto (Long orderId, String contents, Integer star, @Nullable String reviewUrl) {
//        this.orderId = orderId;
        this.star = star;
        this.contents = contents;
        this.reviewUrl = reviewUrl;
    }
}
