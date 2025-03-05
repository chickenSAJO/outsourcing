package xyz.tomorrowlearncamp.outsourcing.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.auth.AuthValidationMessages;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @Schema(description = "이메일", example = "sparta@sparta.com")
    @NotBlank
    @Pattern(regexp = RegexpType.EMAIL, message = AuthValidationMessages.EMAIL_VALIDATION_MESSAGE)
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    @Pattern(regexp = RegexpType.PASSWORD, message = AuthValidationMessages.PASSWORD_VALIDATION_MESSAGE)
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
