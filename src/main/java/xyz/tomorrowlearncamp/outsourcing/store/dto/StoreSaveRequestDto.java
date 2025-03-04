package xyz.tomorrowlearncamp.outsourcing.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

@Getter
public class StoreSaveRequestDto {
    @Size(max = 100)
    private String storeTitle;

    @NotBlank
    @Pattern(regexp = "/^[0-9]{2}:[0-9]{2}:[0-9]{2}$/")
    private String openTime;

    @NotBlank
    @Pattern(regexp = "/^[0-9]{2}:[0-9]{2}:[0-9]{2}$/")
    private String closeTime;

    @Range(min=0, max=99999)
    private int minimumOrder;
}
