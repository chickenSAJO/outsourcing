package xyz.tomorrowlearncamp.outsourcing.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfoUserResponseDto {

    private String email;

    private String nickname;

    @Builder
    public InfoUserResponseDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

}
