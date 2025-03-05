package xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateInfoUserRequestDto {

    @NotNull
    @Size(min = 1, max = 10)
    private String nickname;

    private String address;
}
