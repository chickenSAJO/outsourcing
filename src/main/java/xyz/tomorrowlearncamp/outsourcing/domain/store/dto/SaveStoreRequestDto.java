package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
//import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.etc.RegexpType;

import java.time.LocalTime;

@Getter
//@RequiredArgsConstructor
public class SaveStoreRequestDto {
    @NotBlank
    @Size(max = 100) //100문자까지
    private String storeTitle;

    @NotBlank
    @Pattern(regexp = RegexpType.TIME)
    private String openTime;//00:00:00 형식 //string 변경?

    @NotBlank
    @Pattern(regexp = RegexpType.TIME)
    private String closeTime;//00:00:00 형식 //string 변경?

    @Range(min=0, max=99999) //0원부터 10만원 미만까지 (null허용)
    private int minimumOrder;

    public SaveStoreRequestDto(String storeTitle, String openTime, String closeTime, int minimumOrder) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
    }
}
