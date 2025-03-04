package xyz.tomorrowlearncamp.outsourcing.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.auth.AuthValidationMessages;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @Schema(description = "이메일", example = "sparta@sparta.com")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = EMAIL_VALIDATION_MESSAGE)
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,}$", message = PASSWORD_VALIDATION_MESSAGE)
    private String password;

    @Schema(description = "전화번호")
    @NotBlank
    private String phone;

    @Schema(description = "닉네임")
    @NotBlank
    private String nickname;

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "주소")
    @NotBlank
    private String address;

    @Schema(description = "유저타입")
    @NotBlank
    private String usertype;

    public SignupRequestDto(String email, String password, String phone, String nickname, String name, String address, String usertype) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
        this.address = address;
        this.usertype = usertype;
    }
}
