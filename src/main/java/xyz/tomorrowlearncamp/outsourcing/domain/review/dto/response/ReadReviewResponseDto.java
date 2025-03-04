package xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReadReviewResponseDto {

    private Long id;

    private String contents;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public ReadReviewResponseDto(Long id, String contents, String image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.contents = contents;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
