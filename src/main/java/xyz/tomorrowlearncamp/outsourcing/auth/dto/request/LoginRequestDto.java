package xyz.tomorrowlearncamp.outsourcing.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto implements ValidationMessages {

    @Schema(description = "이메일", example = "sparta@sparta.com")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = EMAIL_VALIDATION_MESSAGE)
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,}$", message = PASSWORD_VALIDATION_MESSAGE)
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
