package xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.entity.CommentEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReadReviewResponseDto {

    private Long reviewId;
    private Long storeId;
    private String reviewContents;
    private String reviewImage;
    private Integer star;
    private String commentContents;

    private LocalDateTime reviewCreatedAt;
    private LocalDateTime reviewUpdatedAt;

    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;

    @Builder
    public ReadReviewResponseDto(ReviewEntity review, CommentEntity comment) {
        this.reviewId = review.getId();
        this.reviewContents = review.getContents();
        this.reviewImage = review.getReviewImageUrl();
        this.star = review.getStar();

        this.storeId = review.getStoreId();
        this.reviewCreatedAt = review.getCreatedAt();
        this.reviewUpdatedAt = review.getUpdatedAt();

        if (comment != null) {
            this.commentContents = comment.getContents();
            this.commentCreatedAt = comment.getCreatedAt();
            this.commentUpdatedAt = comment.getUpdatedAt();
        }
    }
}
