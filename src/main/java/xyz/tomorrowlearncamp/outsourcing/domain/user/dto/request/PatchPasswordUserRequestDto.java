package xyz.tomorrowlearncamp.outsourcing.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;

@Getter
public class PatchPasswordUserRequestDto {

    @NotNull
    private String oldPassword;

    @NotNull
    @Pattern(regexp = RegexpType.PASSWORD)
    private String newPassword;
}
