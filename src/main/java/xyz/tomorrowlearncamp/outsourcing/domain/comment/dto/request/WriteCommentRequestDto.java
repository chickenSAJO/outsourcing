package xyz.tomorrowlearncamp.outsourcing.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WriteCommentRequestDto {

    @NotNull
    @Size(min = 1, max = 100)
    private String comment;
}
