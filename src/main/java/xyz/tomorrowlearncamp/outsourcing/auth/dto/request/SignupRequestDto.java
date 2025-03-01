package xyz.tomorrowlearncamp.outsourcing.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @Schema(description = "이메일", example = "sparta@sparta.com")
    @NotBlank
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,}$", message = "비밀번호는 최소 8글자 이상이어야 하며, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
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

}
