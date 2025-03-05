package xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;

@Getter
public class UpdatePasswordUserRequestDto {

    @NotBlank
    private String oldPassword;

    @NotNull
    @Pattern(regexp = RegexpType.PASSWORD)
    private String newPassword;
}
