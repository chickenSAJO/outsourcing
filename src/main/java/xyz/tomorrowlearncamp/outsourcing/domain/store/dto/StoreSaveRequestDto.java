package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class StoreSaveRequestDto {
    @Size(max = 100) //100문자까지
    private String storeTitle;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2}:[0-9]{2}:[0-9]{2}$") //00:00:00 형식
    private String openTime;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2}:[0-9]{2}:[0-9]{2}$") //00:00:00 형식
    private String closeTime;

    @Range(min=0, max=99999) //0원부터 10만원 미만까지 (null허용)
    private int minimumOrder;

    private User user;

    public StoreSaveRequestDto(String storeTitle, String openTime, String closeTime, int minimumOrder, User user) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.user = user;
    }
}
